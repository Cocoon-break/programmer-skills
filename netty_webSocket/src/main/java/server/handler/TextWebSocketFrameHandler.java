package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by jiang wei on 2017/2/14.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + textWebSocketFrame.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + textWebSocketFrame.text()));
            }
        }
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel c : channels) {
            c.writeAndFlush(new TextWebSocketFrame("Server:" + incoming.remoteAddress()));
        }
        channels.add(ctx.channel());
        System.out.println("Client:" + incoming.remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel c : channels) {
            c.writeAndFlush(new TextWebSocketFrame("Server:" + incoming.remoteAddress()));
        }
        channels.remove(ctx.channel());
        System.out.println("Client:" + incoming.remoteAddress() + "离开");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
