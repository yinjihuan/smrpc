package org.smrpc.core.remoting;

import org.smrpc.core.serialize.RpcRequestDecoder;
import org.smrpc.core.serialize.RpcResponseEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

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
		  ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
	      ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
     	ch.pipeline().addLast("decoder", new RpcRequestDecoder());
     	ch.pipeline().addLast("encoder", new RpcResponseEncoder());
     	ch.pipeline().addLast(new NettyServerHandler());
     }
	 
}
