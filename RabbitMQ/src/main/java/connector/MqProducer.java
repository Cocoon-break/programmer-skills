package connector;

import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
/**
 * Created by jw on 2016/8/28.
 */
public class MqProducer extends BaseConnector {
    public MqProducer(String queueName) throws IOException {
        super(queueName);
    }
    public void sendMessage(Serializable obj) throws IOException {
        channel.basicPublish("",queueName,null, SerializationUtils.serialize(obj));
    }
}
