package org.smrpc.core.remoting;

import org.smrpc.core.rpc.RpcRequest;
import org.smrpc.core.rpc.RpcResponse;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServer {
	
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

	public NettyServer(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public synchronized void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						initClientChannel(ch);
					}
				}).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture f = bootstrap.bind(port).sync();
			 f.channel().closeFuture().sync();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
	        bossGroup.shutdownGracefully();
		}
	}
	
	 public void initClientChannel(SocketChannel ch) {
		// 使用框架自带的对象编解码器
     	ch.pipeline().addLast("decoder", 
     			new ObjectDecoder(
     					ClassResolvers.cacheDisabled(
     							this.getClass().getClassLoader()
     					)
     			)
     	);
     	ch.pipeline().addLast("encoder", new ObjectEncoder());
     	ch.pipeline().addLast("handler", new NettyServerHandler());
     }
	 
}
