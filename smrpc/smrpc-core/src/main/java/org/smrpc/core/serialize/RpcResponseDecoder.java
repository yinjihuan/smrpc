package org.smrpc.core.serialize;

import java.util.List;

import org.smrpc.core.serialize.jdk.JdkSerialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcResponseDecoder extends ByteToMessageDecoder {
	
	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.err.println("RpcResponseDecoder");
        Object obj = JdkSerialize.byteToObject(JdkSerialize.read(in));
        out.add(obj);
    }
	
}