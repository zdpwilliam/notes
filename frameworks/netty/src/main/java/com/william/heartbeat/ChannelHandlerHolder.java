package com.william.heartbeat;

import io.netty.channel.ChannelHandler;

public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();

}
