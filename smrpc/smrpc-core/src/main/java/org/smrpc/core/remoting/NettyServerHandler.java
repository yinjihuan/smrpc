package org.smrpc.core.remoting;

import java.lang.reflect.Method;

import org.smrpc.core.rpc.DefaultRpcRequest;
import org.smrpc.core.rpc.DefaultRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Object value = null;
		DefaultRpcResponse response = null;
		try {
			DefaultRpcRequest request = (DefaultRpcRequest) msg;
			response = new DefaultRpcResponse(request.getRequestId());
			System.err.println("server:" + request.getRequestId());
			/*String className = request.getInterfaceName();
			Class<?> serviceClass = Class.forName(className);
			Object serviceBean = serviceClass.newInstance();
			String methodName = request.getMethodName();
			Class<?>[] parameterTypes = request.getParameterTypes();
			Object[] parameters = request.getArguments();

			Method method = serviceClass.getMethod(methodName, parameterTypes);
			method.setAccessible(true);
			value = method.invoke(serviceBean, parameters);*/
			value = "猿天地";
		} catch (Exception e) {
			response.setException(e);
		}
		response.setValue(value);
		ctx.channel().writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
