package org.smrpc.example.client;

import org.smrpc.core.proxy.JdkProxyFactory;
import org.smrpc.core.proxy.RefererInvocationHandler;
import org.smrpc.core.remoting.NettyClient;
import org.smrpc.core.rpc.DefaultRpcRequest;
import org.smrpc.core.rpc.RpcRequest;
import org.smrpc.example.api.UserRpcService;

/**
 * RPC客户端测试代码
 *
 * @author yinjihuan
 * 
 * @about http://cxytiandi.com/about
 * 
 */
public class ExampleClientApp {
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 4000;
		System.err.println("********");
		NettyClient client = new NettyClient(host, port);
		System.err.println("VVVV");
		//RpcRequest request = new DefaultRpcRequest();
		//client.request(null);
		JdkProxyFactory factory = new JdkProxyFactory();
		UserRpcService userRpcService = factory.getProxy(UserRpcService.class, new RefererInvocationHandler<UserRpcService>(UserRpcService.class, client));
		System.out.println(userRpcService.getName(1001l));
	}
}
