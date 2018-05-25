package org.smrpc.core.serialize;

import org.smrpc.core.rpc.RpcRequest;
import org.smrpc.core.serialize.jdk.JdkSerialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcRequestEncoder extends MessageToByteEncoder<RpcRequest> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, RpcRequest message, ByteBuf out) throws Exception {
		System.err.println("RpcRequestEncoder");
		byte[] datas = JdkSerialize.objectToByte(message);
		out.writeBytes(datas);
		ctx.flush();
	}
	
}