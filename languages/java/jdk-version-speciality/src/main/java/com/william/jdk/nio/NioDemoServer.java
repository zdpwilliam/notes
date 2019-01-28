package com.william.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioDemoServer {
    /**
     * java nio 通信与Bio通信主要不同点:
     *
     * 1. nio中的单个channel即可支持读操作也可以支持写操作,而bio中读操作要用inputstream,
     * 写操作要outputstream
     * 2. nio采用byteBuffer作为内存缓存区,向channel里写或读操作,bio基本是用byte[]
     * 3. nio采用selector组件轮询读取就绪channel
     */
    private static final int PORT = 9001;

    public static void main(String[] args) {
        new Thread(new MultiplexerTimeServer(PORT)).start();
    }

    static class MultiplexerTimeServer implements Runnable {

        private Selector selector;
        private ServerSocketChannel ssChannel;
        private volatile boolean stop;

        public MultiplexerTimeServer(int port) {
            try {
                //1.打开ServerSocketChannel,用于监听客户端的连接，它是所有客户端连接的父管道.
                ssChannel = ServerSocketChannel.open();
                //2.绑定监听端口，设置连接为非阻塞模式
                ssChannel.socket().bind(new InetSocketAddress(port), PORT);
                ssChannel.configureBlocking(false);

                //3.创建多路复用器
                selector = Selector.open();
                //4.将ServerSocketChannel注册到多路复用器Selector上，监听ACCEPT事件
                ssChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("The time server has started in port: " + port);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void stop() {
            this.stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    //5.轮询准备就绪的Key
                    selector.select(1000);
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();

                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    SelectionKey key = null;

                    while (it.hasNext()) {
                        key = it.next();
                        it.remove();

                        try {
                            handleInput(key);
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                //处理新接入的请求消息
                if (key.isAcceptable()) {
                    //6.处理新的接入请求
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    //7.设置客户端链路为非阻塞模式
                    sc.configureBlocking(false);
                    //8.将新接入的客户端连接注册到多路复用器上，监听读操作，读取客户端发送的网络消息
                    sc.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    //9.异步读取客户端请求消息到缓冲区
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        System.out.println("The time server receive order: " + body);
                        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                        doWrite(sc, currentTime);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        System.out.println("0 size bytes");
                    }
                }
            }
        }

        private void doWrite(SocketChannel channel, String response) throws IOException {
            //10.将消息异步发送给客户端
            if (response != null && response.trim().length() > 0) {
                byte[] bytes = response.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                channel.write(writeBuffer);
            }
        }
    }
}
