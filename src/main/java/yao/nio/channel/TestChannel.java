package yao.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * FileChannel
 * SocketChannel
 * ServerSocketChannel
 * DatagramChannel
 */
public class TestChannel {
    public static void main(String[] args) {
//        testStream();
//        testOpen();

        testTransfer();
    }

    private static void testTransfer() {
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("D:\\NIOTest\\1.png"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("D:\\NIOTest\\4.png"), StandardOpenOption.READ,
                    StandardOpenOption.WRITE,StandardOpenOption.CREATE_NEW);

//            outChannel.transferFrom(inChannel, 0, inChannel.size());
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试.open方法
     */
    private static void testOpen() {
        try {
            /**
             * 创建通道
             */
            FileChannel inChannel = FileChannel.open(Paths.get("D:\\NIOTest\\1.png"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("D:\\NIOTest\\3.png"), StandardOpenOption.READ,
                    StandardOpenOption.WRITE,StandardOpenOption.CREATE_NEW);

            /**
             *创建直接内存映射缓冲区
             */
            MappedByteBuffer inByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            /**
             * 不通过通道，直接通过缓冲区读写数据
             */
            byte[] bytes = new byte[inByteBuffer.limit()];
            inByteBuffer.get(bytes);
            outByteBuffer.put(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Stream
     */
    public static void testStream() {
        FileInputStream fis=null;
        FileOutputStream fos=null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("D:\\NIOTest\\1.png");
            fos = new FileOutputStream("D:\\NIOTest\\2.png");

            /**
             * 获取对应通道
             */
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            /**
             * 建立缓冲区
             */
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            /**
             * 将数据读到缓冲区
             */
            while (inChannel.read(byteBuffer) != -1) {
                /**
                 * 切换到读数据模式
                 */
                byteBuffer.flip();

                /**
                 * 数据写入缓冲区
                 */
                outChannel.write(byteBuffer);
                /**
                 * 清空缓冲区
                 */
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outChannel.close();
                inChannel.close();

                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
