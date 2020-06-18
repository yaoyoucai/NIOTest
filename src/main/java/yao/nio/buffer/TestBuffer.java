package yao.nio.buffer;

import java.nio.ByteBuffer;

/**
 * buffer测试类
 */
public class TestBuffer {
    public static void main(String[] args) {
        String value="abcde";

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("------------------------");
        System.out.println("position:"+byteBuffer.position());
        System.out.println("limit:"+byteBuffer.limit());
        System.out.println("capacity:"+byteBuffer.capacity());

        System.out.println("------------------------put");
        byteBuffer.put(value.getBytes());
        System.out.println("position:"+byteBuffer.position());
        System.out.println("limit:"+byteBuffer.limit());
        System.out.println("capacity:"+byteBuffer.capacity());

        System.out.println("------------------------");
        /**
         * 需要将buffer从写数据状态变为读数据状态
         */
        byteBuffer.flip();
        byte[] bytes = new byte[value.length()];
        byteBuffer.get(bytes);
        System.out.println("result:"+new String(bytes));
        System.out.println("position:"+byteBuffer.position());
        System.out.println("limit:"+byteBuffer.limit());
        System.out.println("capacity:"+byteBuffer.capacity());

        byteBuffer.rewind();

    }


}
