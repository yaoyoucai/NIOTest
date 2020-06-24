package yao.nio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 测试分散和聚集
 */
public class TestScatterAndGatter {
    public static void main(String[] args) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/huanyao/IdeaProjects/NIOTest/1.text", "rw");
            FileChannel inChannel = randomAccessFile.getChannel();

            ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
            ByteBuffer[] dst = new ByteBuffer[1];
            dst[0] = byteBuffer1;
            inChannel.read(dst);

            for (ByteBuffer byteBuffer : dst) {
                byteBuffer.flip();
            }

            RandomAccessFile file1 = new RandomAccessFile("/Users/huanyao/IdeaProjects/NIOTest/2.text", "rw");
            FileChannel outChannel = file1.getChannel();
            outChannel.write(dst);

            outChannel.close();
            inChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
