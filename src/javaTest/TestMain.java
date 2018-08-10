package javaTest;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestMain {
    public static void main(String[] args) {
//        channelTestOne();
//        channelFromOtherChannel();
        channelToOtherChannel();
    }

    private static void channelTestOne() {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("data/nio-data.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                System.out.println("Read " + bytesRead);
                //将buffer从写切换成读
                buf.flip();
                while(buf.hasRemaining()){
                    System.out.print((char) buf.get());
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
            aFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void channelFromOtherChannel(){
        try {
            RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
            FileChannel fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
            FileChannel      toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();

            toChannel.transferFrom(fromChannel, position, count);
            fromFile.close();
            toFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void channelToOtherChannel(){
        try {
            RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
            FileChannel      fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
            FileChannel      toChannel = toFile.getChannel();

            long position = 0;
            long count    = fromChannel.size();

            fromChannel.transferTo(position, count, toChannel);
            fromFile.close();
            toFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
