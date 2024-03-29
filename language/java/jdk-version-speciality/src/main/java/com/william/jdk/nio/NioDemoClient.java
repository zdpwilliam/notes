package com.william.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioDemoClient {
    /**
        使用NIO编程的优点:
        1. 客户端发起的连接操作是异步的，可以通过在多路复用器注册OP_CONNECT等待后续结果，不需要像之前的客户端那样被同步阻塞.
        2. SocketChannel的读写操作都是异步的，如果没有可读写的数据它不会同步等待，直接返回，这样I/O通信线程就可以处理其他的链路，
        不需要同步等待这个链路可用。
        3. 线程模型的优化：由于JDK的Selector在Linux等主流操作系统上通过epoll实现，它没有连接句柄数的限制（只受限于操作系统的最
        大句柄数或者单个进程的句柄限制），这意味着一个Selector线程可以同时处理成千上万个客户端连接，而且性能不会随着客户端的增加
        而线性下降。因此，它非常适合做高性能、高负载的网络服务器。
     */

    private static final int PORT = 9001;

    public static void main(String[] args) {
        new Thread(new TimeClinetHandle("127.0.0.1", PORT)).start();
    }

    static class TimeClinetHandle implements Runnable {

        private String host;
        private int port;
        private Selector selector;
        private SocketChannel sChannel;
        private volatile boolean stop;

        public TimeClinetHandle(String host, int port) {
            this.host = host == null ? "127.0.0.1" : host;
            this.port = port;
            try {
                //1.打开SocketChannel
                sChannel = SocketChannel.open();
                //2.设置SocketChannel为非阻塞模式
                sChannel.configureBlocking(false);

                //4.创建多路复用器
                selector = Selector.open();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        @Override
        public void run() {
            try {
                doConnect();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            while (!stop) {
                try {
                    //5.轮询准备就绪的key
                    selector.select(1000);
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
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
                    System.exit(1);
                }
            }

            //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                SocketChannel sc = (SocketChannel) key.channel();
                //6.接收connect事件进行处理
                if (key.isConnectable()) {
                    //7.连接成功，注册读事件到多路复用器
                    if (sc.finishConnect()) {
                        sc.register(selector, SelectionKey.OP_READ);
                        doWrite(sc);
                    } else {
                        System.exit(1);
                    }
                }

                //8.异步读服务器返回消息到缓冲区
                if (key.isReadable()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        System.out.println("Now is: " + body);
//                        this.stop = true;
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        System.out.println("0 size bytes.");
                    }
                }
            }
        }

        private void doConnect() throws IOException {
            //3.异步连接服务器
            //成功，注册
            if (sChannel.connect(new InetSocketAddress(host, port))) {
                sChannel.register(selector, SelectionKey.OP_READ);
                doWrite(sChannel);
            } else {//注册，监听服务器的ack
                sChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        }

        private void doWrite(SocketChannel sc) throws IOException {
            //9.调用SocketChannel的异步write接口，将消息异步发送给服务器
            byte[] req = "QUERY TIME ORDER".getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
            writeBuffer.put(req);
            writeBuffer.flip();
            sc.write(writeBuffer);
            if (!writeBuffer.hasRemaining()) {
                System.out.println("Send order 2 server succeed.");
            }
        }
    }
}
