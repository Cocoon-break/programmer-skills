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
public class HttpClientForApache {
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;


    public HttpClientForApache() {
        httpClient = HttpClients.createDefault();
        httpPost = new HttpPost("http://127.0.0.1:9999/user");
    }


    public void connectNettyServer() throws IOException {
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("yourName", "name"));
            params.add(new BasicNameValuePair("yourNum", "22"));
            httpPost.setEntity(new UrlEncodedFormEntity(params));


            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
            BufferedReader br= new BufferedReader(new InputStreamReader(content));
            StringBuilder sb=new StringBuilder();

            String line=null;
            try{
                while((line=br.readLine())!=null){
                    sb.append(line);
                }

            }finally {
                    content.close();
            }

            System.out.println(sb.toString());
        }catch (Exception e){
            System.out.println("error:"+e);
        }finally {
            httpPost.abort();
            httpClient.close();
        }

    }

}
