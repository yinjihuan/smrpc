package org.smrpc.core.remoting;

import org.smrpc.core.rpc.DefaultRpcRequest;
import org.smrpc.core.rpc.RpcRequest;
import org.smrpc.core.serialize.RpcRequestEncoder;
import org.smrpc.core.serialize.RpcResponseDecoder;
import org.smrpc.core.serialize.RpcResponseEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

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
			System.err.println("NNN");
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	 public void initClientChannel(SocketChannel ch) {
		 ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
	     ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
		 ch.pipeline().addLast("decoder", new RpcResponseDecoder());
	     ch.pipeline().addLast("encoder", new RpcRequestEncoder());
     	 ch.pipeline().addLast(new NettyClientHandler());
     }
	 
	 public void request(RpcRequest request) {
		 DefaultRpcRequest r = new DefaultRpcRequest();
		 System.out.println(channel);
		 this.channel.writeAndFlush(r);
		 System.out.println(channel+"enmd");
	 }
	 
}
