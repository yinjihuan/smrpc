package org.smrpc.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.smrpc.core.common.Constants;
import org.smrpc.core.remoting.NettyClient;
import org.smrpc.core.remoting.exchange.support.DefaultFuture;
import org.smrpc.core.rpc.DefaultRpcRequest;
import org.smrpc.core.util.RequestIdGenerator;

public class RefererInvocationHandler<T> implements InvocationHandler {

	private Class<T> clz;
	 
	private String interfaceName;
	
	private NettyClient nettyClient;
	
	public RefererInvocationHandler(Class<T> clz, NettyClient nettyClient) {
		super();
		this.clz = clz;
		this.interfaceName = clz.getName();
		this.nettyClient = nettyClient;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		DefaultRpcRequest request = new DefaultRpcRequest();
        request.setRequestId(RequestIdGenerator.getRequestId());
        request.setArguments(args);
	    request.setMethodName(method.getName());
        request.setInterfaceName(method.getDeclaringClass().getName());
        request.setParameterTypes(method.getParameterTypes());
        DefaultFuture future = new DefaultFuture(nettyClient.getChannel(), request, Constants.DEFAULT_TIMEOUT);
        future.send(request);
		return future.get();
	}
	
	/**
     * tostring,equals,hashCode,finalize等接口未声明的方法不进行远程调用
     *
     * @param method
     * @return
     */
    public boolean isLocalMethod(Method method) {
        if (method.getDeclaringClass().equals(Object.class)) {
            try {
                Method interfaceMethod = clz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                return false;
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

}
