# Android图片三级缓存机制
## 为何引入三级缓存的概念
* app过多的访问网络图片，造成用户流量流失过快

## 图片三级缓存的的概念
* 网络图片缓存：不优先加载，速度慢，耗费流量
* 本地图片缓存：次优先加载，速度快
* 内存图片缓存：最先加载，速度最快

## 图片三级缓存的原理
* app首次启动时，加载完成网络图片之后将其保存到内存中和sd卡当中
* 之后运行app的时候，会先从内存里查找图片，如果能找到，直接使用内存中的图片，否则从sd卡里查找。

## 具体的实现
###1.自定义图片缓存工具类ImageCacheUtil
* 通过new ImageCacheUtil().display(ImageView imageView, String url) 提供给外部方法进行图片缓存的接口
* 参数含义：imageView 用于显示图片的ImageView，url 获取图片的网络地址

> 自定义的ImageCacheUtil,实现三级缓存
     
    public class ImageCacheUtil {

        private NetImageCache mNetImageCache;
        private LocalImageCache mLocalImageCache;
        private MemeryImageCache mMemeryImageCache;

        public ImageCacheUtil(){
            mMemeryImageCache=new MemeryImageCache();
            mLocalImageCache=new LocalImageCache();
            mNetImageCache=new NetImageCache(mLocalImageCache,mMemeryImageCache);
        }

        public void disPlay(ImageView imageView, String url) {
            imageView.setImageResource(R.mipmap.pic_item_list_default);
            Bitmap bitmap;
            //内存缓存
            bitmap=mMemeryImageCache.getBitmapFromMemory(url);
            if (bitmap!=null){
                imageView.setImageBitmap(bitmap);
                System.out.println("从内存获取图片啦.....");
                return;
            }

            //本地缓存
            bitmap = mLocalImageCache.getBitmapFromLocal(url);
            if(bitmap !=null){
                imageView.setImageBitmap(bitmap);
                System.out.println("从本地获取图片");
                //从本地获取图片后,保存至内存中
                mMemeryImageCache.setBitmapToMemory(url,bitmap);
                return;
            }
            //网络缓存
            mNetImageCache.getBitmapFromNet(imageView,url);
        }
    }
###2.网络图片缓存（NetImageCache）
* 网络缓存中主要用到了AsyncTask来进行异步数据的加载
* 简单来说，AsyncTask可以看作是一个对handler和线程池的封装，通常，AsyncTask主要用于数据简单时，handler+thread主要用于数据量多且复杂时，当然这也不是必须的，仁者见仁智者见智
* 同时，为了避免内存溢出的问题，我们可以在获取网络图片后。对其进行图片压缩

>网络图片缓存（NetImageCache）

	public class NetImageCache {

        private LocalImageCache mLocalImageCache;
        private MemeryImageCache mMemeryImageCache;

        public NetImageCache(LocalImageCache LocalImageCache, MemeryImageCache MemeryImageCache) {
            mLocalImageCache = LocalImageCache;
            mMemeryImageCache = MemeryImageCache;
        }

        /**
         * 从网络下载图片
         * @param imageView 显示图片的imageview
         * @param url   下载图片的网络地址
         */
        public void getBitmapFromNet(ImageView imageView, String url) {
            new BitmapTask().execute(imageView, url);//启动AsyncTask

        }

        /**
         * AsyncTask就是对handler和线程池的封装
         * 第一个泛型:参数类型
         * 第二个泛型:更新进度的泛型
         * 第三个泛型:onPostExecute的返回结果
         */
        class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

            private ImageView imageView;
            private String url;

            /**
             * 后台耗时操作,存在于子线程中
             * @param params
             * @return
             */
            @Override
            protected Bitmap doInBackground(Object[] params) {
                imageView = (ImageView) params[0];
                url = (String) params[1];

                return downLoadBitmap(url);
            }

            /**
             * 更新进度,在主线程中
             * @param values
             */
            @Override
            protected void onProgressUpdate(Void[] values) {
                super.onProgressUpdate(values);
            }

            /**
             * 耗时方法结束后执行该方法,主线程中
             * @param result
             */
            @Override
            protected void onPostExecute(Bitmap result) {
                if (result != null) {
                    imageView.setImageBitmap(result);
                    System.out.println("从网络缓存图片");

                    //从网络获取图片后,保存至本地缓存
                    mLocalImageCache.setBitmapToLocal(url, result);
                    //保存至内存中
                    mMemeryImageCache.setBitmapToMemory(url, result);

                }
            }
        }

        /**
         * 网络下载图片
         * @param url
         * @return
         */
        private Bitmap downLoadBitmap(String url) {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    //图片压缩
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize=2;//宽高压缩为原来的1/2
                    options.inPreferredConfig=Bitmap.Config.ARGB_4444;
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null,options);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }

            return null;
        }
    }
###3.本地缓存也叫磁盘缓存或sd卡缓存(LocalImageCache)
* 在初次通过网络获取图片后，我们可以在本地SD卡中将图片保存起来
* 可以使用MD5加密图片的网络地址，来作为图片的名称保存

>本地图片缓存（LocalImageCache）


		public class LocalImageCache {

        private static final String CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/WerbNews";

        /**
         * 从本地读取图片
         * @param url
         */
        public Bitmap getBitmapFromLocal(String url){
            String fileName = null;//把图片的url当做文件名,并进行MD5加密
            try {
                fileName = MD5Encoder.encode(url);
                File file=new File(CACHE_PATH,fileName);

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 从网络获取图片后,保存至本地缓存
         * @param url
         * @param bitmap
         */
        public void setBitmapToLocal(String url,Bitmap bitmap){
            try {
                String fileName = MD5Encoder.encode(url);//把图片的url当做文件名,并进行MD5加密
                File file=new File(CACHE_PATH,fileName);

                //通过得到文件的父文件,判断父文件是否存在
                File parentFile = file.getParentFile();
                if (!parentFile.exists()){
                    parentFile.mkdirs();
                }

                //把图片保存至本地
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

###4.内存缓存（MemeryImageCache）
* 这是本文的重点
* android进行内存缓存的时候，要注意一个重要的问题：内存溢出（OOM）,为了让我么的app可以使用内存缓存，而且不会出现内存溢出。这里就得引入[android内存管理机制]()
* 内存缓存的实现方式

通过 HashMap<String,Bitmap>键值对的方式保存图片，key为地址，value为图片对象，但因是强引用对象，很容易造成内存溢出，可以尝试SoftReference软引用对象

通过 HashMap<String, SoftReference<Bitmap>>SoftReference 为软引用对象（GC垃圾回收会自动回收软引用对象），但在Android2.3+后，系统会优先考虑回收弱引用对象，官方提出使用LruCache

通过 LruCache<String,Bitmap> least recentlly use 最少最近使用算法
会将内存控制在一定的大小内, 超出最大值时会自动回收, 这个最大值开发者自己定

	/**
     * 三级缓存之内存缓存
     */
    public class MemeryImageCache {

        // private HashMap<String,Bitmap> mMemoryCache=new HashMap<>();//1.因为强引用,容易造成内存溢出，所以考虑使用下面弱引用的方法
        // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();//2.因为在Android2.3+后,系统会优先考虑回收弱引用对象,官方提出使用LruCache
        private LruCache<String,Bitmap> mMemoryCache;

        public MemeryImageCache(){
            long maxMemory = Runtime.getRuntime().maxMemory()/8;//得到手机最大允许内存的1/8,即超过指定内存,则开始回收
            //需要传入允许的内存最大值,虚拟机默认内存16M,真机不一定相同
            mMemoryCache=new LruCache<String,Bitmap>((int) maxMemory){
                //用于计算每个条目的大小
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    int byteCount = value.getByteCount();
                    return byteCount;
                }
            };

        }

        /**
         * 从内存中读图片
         * @param url
         */
        public Bitmap getBitmapFromMemory(String url) {
            //Bitmap bitmap = mMemoryCache.get(url);//1.强引用方法
            /*2.弱引用方法
            SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
            if (bitmapSoftReference != null) {
                Bitmap bitmap = bitmapSoftReference.get();
                return bitmap;
            }
            */
            Bitmap bitmap = mMemoryCache.get(url);
            return bitmap;

        }

        /**
         * 往内存中写图片
         * @param url
         * @param bitmap
         */
        public void setBitmapToMemory(String url, Bitmap bitmap) {
            //mMemoryCache.put(url, bitmap);//1.强引用方法
            /*2.弱引用方法
            mMemoryCache.put(url, new SoftReference<>(bitmap));
            */
            mMemoryCache.put(url,bitmap);
        }
    }


 