package yao.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestChannel1 {
    public static void main(String[] args) {
        FileChannel inChannel=null;
        FileChannel outChannel=null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/huanyao/IdeaProjects/NIOTest/1.text", "rw");
            //1.创建一个通道
            inChannel = randomAccessFile.getChannel();

            //2.创建一个缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //3.循环写入数据到缓冲区
            while (inChannel.read(byteBuffer) != -1) {
               //4.由写模式切换为读模式
               byteBuffer.flip();

               //5.输出到指定文件
               outChannel = FileChannel.open(Paths.get("/Users/huanyao/IdeaProjects/NIOTest/55.text"),
                       StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
               outChannel.write(byteBuffer);
               byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outChannel.close();
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
