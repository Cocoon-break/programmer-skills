package nettyHttpServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by jiang wei on 2016/9/27.
 */
//这里就是controller了，处理request和response
public class HttpServerInBoundHandler extends ChannelInboundHandlerAdapter {
    private FullHttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        * 说一下这个Time,这个是取毫微秒，System.currentTimeMillis()取毫秒
        * 1秒=1000毫秒
        * 1毫秒=1000微秒
        * 1微秒=1000毫微秒
        *
        * 所以。。nanoTime是相当准确的时间了。。。
        * */
        long start = System.nanoTime();
        /*
        * 之所以用FullHttpRequest是因为，
        * 之前遇到过奇葩的错误，
        * 在普通HttpRequest下，
        * 当client的json比较大的时候会出现json截断的问题
        * */
        if (msg instanceof FullHttpRequest) {
            //这里可以去取header之类的东西
            request = (FullHttpRequest) msg;
            System.out.println("Uri:" + request.getUri());
        }
        if (msg instanceof HttpContent) {
            //这里来做content的相关处理吧
            try {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                System.out.println(buf.toString(CharsetUtil.UTF_8));
                buf.release();
            } catch (Exception e) {
                System.out.println("bad bad bad");
            }

            /*
            *这中间可以写service,dao,等等的东西。。。感兴趣的话，大家fork一下代码，然后自由发挥就行。
            * */


            //response相关。。。
            String res = "get request,success connection";
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            if (HttpUtil.isKeepAlive(request)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
        long end = System.nanoTime();

        long resultTime = end - start;
        //计算每次请求到返回的用时
        System.out.println(resultTime);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
