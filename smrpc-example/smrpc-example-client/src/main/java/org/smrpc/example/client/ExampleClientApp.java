package org.smrpc.example.client;

import java.util.concurrent.atomic.AtomicLong;

import org.smrpc.core.remoting.NettyClient;
import org.smrpc.core.rpc.DefaultRpcRequest;
import org.smrpc.core.rpc.RpcRequest;

/**
 * RPC客户端测试代码
 *
 * @author yinjihuan
 * 
 * @about http://cxytiandi.com/about
 * 
 */
public class ExampleClientApp {
	 private static final AtomicLong INVOKE_ID = new AtomicLong(0);
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 4000;
		NettyClient client = new NettyClient(host, port);
		RpcRequest request = new DefaultRpcRequest();
		client.request(request);
/*		while(true)
			System.out.println(INVOKE_ID.getAndIncrement());
*/		
	}
}
