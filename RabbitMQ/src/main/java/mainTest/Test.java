package mainTest;

import connector.MqConsumer;
import connector.MqProducer;
import messageEntity.MessageData;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jw on 2016/8/28.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        MqConsumer mqConsumer=new MqConsumer("test");
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.submit(mqConsumer);
        MqProducer mqProducer=new MqProducer("test");
        for (int i=0;i<10000;i++){
            MessageData messageData=new MessageData();
            messageData.setChannel("test_mq_1");
            messageData.setContent("msg"+i);
            mqProducer.sendMessage(messageData);
        }

    }
}
