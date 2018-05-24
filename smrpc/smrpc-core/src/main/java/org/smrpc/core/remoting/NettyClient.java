package org.smrpc.core.remoting;

import org.smrpc.core.rpc.RpcRequest;
import org.smrpc.core.rpc.RpcResponse;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClient {
	
	private Channel channel;
	
	private String host;
	
	private int port;
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
		this.open();
	}
	
	public synchronized boolean open() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						initClientChannel(ch);
					}
				});
			ChannelFuture f = b.connect(host, port);
			this.channel = f.channel();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	 public void initClientChannel(SocketChannel ch) {
		// 使用框架自带的对象编解码器
     	ch.pipeline().addLast(
     			new ObjectDecoder(
     					ClassResolvers.cacheDisabled(
     							this.getClass().getClassLoader()
     					)
     			)
     	);
     	ch.pipeline().addLast(new ObjectEncoder());
     	ch.pipeline().addLast(new NettyClientHandler());
     }
	 
	 public RpcResponse request(RpcRequest request) {
		 return doRequest(request);
	 }
	 
	 private RpcResponse doRequest(RpcRequest request) {
		 this.channel.writeAndFlush(request);
		 return null;
	 }
}
