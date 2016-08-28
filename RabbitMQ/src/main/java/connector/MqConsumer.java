package connector;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import messageEntity.MessageData;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;

/**
 * Created by jw on 2016/8/28.
 * 带注释的两个方法是基本方法，其余方法可以再完成基本功能之后再研究
 */
public class MqConsumer extends BaseConnector implements Runnable,Consumer {
    public MqConsumer(String queueName) throws IOException {
        super(queueName);
    }

    //消费者注册成功时，自动调用
    public void handleConsumeOk(String s) {
        System.out.println("Consumer"+s+"Registered");
    }

    public void handleCancelOk(String s) {

    }

    public void handleCancel(String s) throws IOException {

    }

    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    public void handleRecoverOk(String s) {

    }
    //消费者收到消息会自动调用
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        MessageData messageData= (MessageData) SerializationUtils.deserialize(bytes);
        System.out.println("channel:"+messageData.getChannel()+"----"+"content:"+messageData.getContent());
    }
    //实现run方法，多线程用
    public void run() {
        try {
            channel.basicConsume(queueName, true,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
