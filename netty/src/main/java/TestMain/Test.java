package TestMain;

import httpClient.HttpClientForApache;
import httpClientThread.ClientRun;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiang wei on 2016/9/27.
 */
public class Test {
    public static void main(String[] args) throws IOException {
       ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            executorService.submit(new ClientRun());
        }

    }
}
