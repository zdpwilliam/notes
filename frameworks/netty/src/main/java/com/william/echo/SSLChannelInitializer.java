package com.william.echo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

@ChannelHandler.Sharable
public class SSLChannelInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;
    private final boolean startTls;

    public SSLChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) {
        SSLEngine engine = context.newEngine(ch.alloc());
        ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
    }
}
