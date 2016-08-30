package messageEntity;

import java.io.Serializable;

/**
 * Created by jw on 2016/8/28.
 */
public class MessageData implements Serializable {

    private static final long serialVersionUID = 3250977593758869279L;
    //消息的传递通道。。可以这么理解。。。
    private String channel;
    //消息的内容
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
