package org.smrpc.example.server;

import org.smrpc.core.remoting.NettyServer;
import org.smrpc.core.remoting.NettyServerHandler;


/**
 * Hello world!
 *
 */
public class ExampleServerApp {
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 4000;
		NettyServer server = new NettyServer(host, port);
		server.run();
	}
	
	
}
