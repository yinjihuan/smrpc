package org.smrpc.core.serialize;

import org.smrpc.core.rpc.RpcResponse;
import org.smrpc.core.serialize.jdk.JdkSerialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcResponseEncoder extends MessageToByteEncoder<RpcResponse> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, RpcResponse message, ByteBuf out) throws Exception {
		System.err.println("RpcResponseEncoder");
		byte[] datas = JdkSerialize.objectToByte(message);
		out.writeBytes(datas);
		ctx.flush();
	}
	
}