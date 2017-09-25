package util;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jiang wei on 2017/9/18.
 */
public class ConfigInit {



    private static GenericObjectPool<StatefulRedisConnection<String, String>> pool;

    public void initProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
        properties.load(inputStream);
        RedisClient client = RedisClient.create(RedisURI.create(properties.getProperty("redis.host"), Integer.valueOf(properties.getProperty("redis.port"))));
        pool = ConnectionPoolSupport.createGenericObjectPool(client::connect, new GenericObjectPoolConfig());
    }


    public static RedisAsyncCommands<String, String> getRedisConnection() throws Exception {
        return pool.borrowObject().async();
    }

}
