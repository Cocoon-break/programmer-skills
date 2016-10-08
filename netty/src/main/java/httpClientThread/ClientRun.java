package httpClientThread;

import httpClient.HttpClientForApache;

import java.io.IOException;

/**
 * Created by jw on 16-9-27.
 */
public class ClientRun implements Runnable{
/*
* 这个搞点有意思的，写一个Runnable，多线程发送http请求，并且记录相关时间
*
* */
    public void run() {
        HttpClientForApache httpClientForApache=new HttpClientForApache();
        try {
            long start = System.nanoTime();
            httpClientForApache.connectNettyServer();
            long end = System.nanoTime();
            long result=end-start;
            System.out.println(Thread.currentThread().getName()+"--->"+result);
        } catch (IOException e) {
            System.out.println("error:"+e);
        }
    }
}
