package util;

import com.alibaba.fastjson.JSONObject;
import entity.UserMapper;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

/**
 * Created by jiang wei on 2017/9/18.
 */
public class HttpContentUtil {
    public static JSONObject generateHttpContentJson(ByteBuf content) {
        if (content == null) {
            return new JSONObject();
        }
        String contentStr = content.toString(CharsetUtil.UTF_8);
        UserMapper userMapper = JSONObject.parseObject(contentStr, UserMapper.class);
        return null;
    }
}
