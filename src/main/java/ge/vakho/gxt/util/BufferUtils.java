package ge.vakho.gxt.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Function;

public class BufferUtils {

    public static <R> R readBytes(RandomAccessFile raf, int byteSize, Function<ByteBuffer, R> function) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(byteSize);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        try {
            byte[] bytes = new byte[byteSize];
            if (raf.read(bytes) == -1) {
                throw new IllegalStateException(byteSize + " bytes not present");
            }
            bb.put(bytes);
            bb.rewind();
            return function.apply(bb);
        } finally {
            bb.clear();
        }
    }

}
