package server.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

/**
 * Created by jiang wei on 2017/2/13.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (wsUri.equalsIgnoreCase(fullHttpRequest.uri())) {
            channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
        } else {
            if (HttpUtil.is100ContinueExpected(fullHttpRequest)) {
                send100Continue(channelHandlerContext);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("connect", Boolean.TRUE);

            HttpResponse response = new DefaultHttpResponse(fullHttpRequest.protocolVersion(), HttpResponseStatus.OK);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");

            boolean keepAlive = HttpUtil.isKeepAlive(fullHttpRequest);
            if (keepAlive) {
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, jsonObject.size());
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            channelHandlerContext.write(response);
            ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
//            file.close();
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client" + incoming.remoteAddress() + "异常");
        cause.printStackTrace();
        ctx.close();
    }


}
