package org.smrpc.core.remoting;

import org.smrpc.core.remoting.exchange.support.DefaultFuture;
import org.smrpc.core.rpc.DefaultRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.err.println("pppppppppppp");
		DefaultRpcResponse response = (DefaultRpcResponse) msg;
		DefaultFuture.received(ctx.channel(), response);
		System.err.println("client:" + response.getRequestId());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}
