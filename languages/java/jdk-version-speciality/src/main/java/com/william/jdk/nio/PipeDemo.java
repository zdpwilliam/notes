package com.william.jdk.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {

    /**
     *  ThreadA --> SinkChannel(Pipe) --> SourceChannel(Pipe) --> ThreadB
     *
     * @throws IOException
     */
    public void pipe() throws IOException {

        Pipe pipe = Pipe.open();

        //ThreadA
        Pipe.SinkChannel sinkChannel = pipe.sink();
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        buffer.clear();
        buffer.put(newData.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            sinkChannel.write(buffer);
        }

        //Thread B
        Pipe.SourceChannel sourceChannel = pipe.source();
        int readSize = sourceChannel.read(buffer);
    }
}
