package TestMain;

import httpClient.HttpClientForApache;

import java.io.IOException;

/**
 * Created by jiang wei on 2016/9/27.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        HttpClientForApache httpClientForApache = new HttpClientForApache();
         httpClientForApache.connectNettyServer();

    }
}
