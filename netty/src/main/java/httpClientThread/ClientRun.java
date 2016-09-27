package httpClientThread;

import httpClient.HttpClientForApache;

import java.io.IOException;

/**
 * Created by jw on 16-9-27.
 */
public class ClientRun implements Runnable{

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
