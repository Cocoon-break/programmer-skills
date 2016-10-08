package httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang wei on 2016/9/27.
 */
/*
* httpclient用的是apache的，肯定有很多人问。。netty也有client啊，为毛不用呢。。。
* 答案是，单纯的觉得apache的简单，所以用，netty的client写出来跟写一套nettySever的量几乎相当。。。
* 真心觉得没必要这么费劲。。。所以，netty的全套狂热粉尽请谅解。。。。
* */
public class HttpClientForApache {
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;


    public HttpClientForApache() {
        httpClient = HttpClients.createDefault();
        //创建httpPost
        httpPost = new HttpPost("http://127.0.0.1:9999/user");
    }


    public void connectNettyServer() throws IOException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //设置请求参数，当然这里随意设一下
            params.add(new BasicNameValuePair("yourName", "name"));
            params.add(new BasicNameValuePair("yourNum", "22"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            //发请求啦。。。
            HttpResponse response = httpClient.execute(httpPost);
            //解析response的流吧。。。又是一段冗长的代码。。。后续更新吧。。
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader br= new BufferedReader(new InputStreamReader(content));
            StringBuilder sb=new StringBuilder();

            String line;
            /*
            * 这几个关的好像有点问题，容我想想。。。。
            * */
            try{
                while((line=br.readLine())!=null){
                    sb.append(line);
                }

            }finally {
                    //别忘了关
                    content.close();
            }

            System.out.println(sb.toString());
        }catch (Exception e){
            System.out.println("error:"+e);
        }finally {
            //别忘了关
            httpPost.abort();
            httpClient.close();
        }

    }

}
