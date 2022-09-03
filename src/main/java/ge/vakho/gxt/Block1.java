package ge.vakho.gxt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static ge.vakho.gxt.util.BufferUtils.readBytes;

public class Block1 {

    protected final byte[] tkeyBlockName = new byte[]{'T', 'K', 'E', 'Y'};
    protected long tkeyBlockSize;
    protected List<TKEY> tkeyEntries;

    public static Block1 from(Map<String, String> map) {
        Block1 block1 = new Block1();

        // Set TKEY block size
        block1.tkeyBlockSize = map.size() * 12L; // Each TKEY block is 12 bytes (0xC)

        // Set TKEY entries
        block1.tkeyEntries = new ArrayList<>(map.size());
        long tdatOffset = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            block1.tkeyEntries.add(new TKEY(tdatOffset, entry.getKey()));
            tdatOffset += (long) entry.getValue().length() * Character.BYTES + 2; // Last two bytes is null symbol!
        }
        return block1;
    }

    public static Block1 from(RandomAccessFile raf) throws IOException {
        Block1 block1 = new Block1();

        // Skip TKEY string
        raf.seek(4);

        // Read TKEY block size
        block1.setTkeyBlockSize((long) readBytes(raf, 4, ByteBuffer::getInt));

        // Iterate over TKEY entries
        block1.tkeyEntries = new ArrayList<>();
        for (long _ignore = 0; _ignore < block1.getTkeyBlockSize() / 12; _ignore++) {
            block1.tkeyEntries.add(TKEY.from(raf));
        }
        return block1;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        // Write block name "TKEY"
        outputStream.write(tkeyBlockName);

        // Write TKEY block size (each TKEY entry is 0xC bytes)
        ByteBuffer bb4 = ByteBuffer.allocate(4); // 4 bytes buffer
        bb4.order(ByteOrder.LITTLE_ENDIAN);
        bb4.putInt(tkeyEntries.size() * 12); // SIZE * 12 bytes
        outputStream.write(bb4.array()); // Store in 4 bytes

        // Each entry is 12 bytes
        for (TKEY tkey : tkeyEntries) {
            tkey.writeTo(outputStream);
        }
    }

    public byte[] getTkeyBlockName() {
        return tkeyBlockName;
    }

    public long getTkeyBlockSize() {
        return tkeyBlockSize;
    }

    public void setTkeyBlockSize(long tkeyBlockSize) {
        this.tkeyBlockSize = tkeyBlockSize;
    }

    public List<TKEY> getTkeyEntries() {
        return tkeyEntries;
    }

    public void setTkeyEntries(List<TKEY> tkeyEntries) {
        this.tkeyEntries = tkeyEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block1 block1 = (Block1) o;
        return tkeyBlockSize == block1.tkeyBlockSize && Arrays.equals(tkeyBlockName, block1.tkeyBlockName) && Objects.equals(tkeyEntries, block1.tkeyEntries);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tkeyBlockSize, tkeyEntries);
        result = 31 * result + Arrays.hashCode(tkeyBlockName);
        return result;
    }

    @Override
    public String toString() {
        return "Block1{" +
                "tkeyBlockName=" + Arrays.toString(tkeyBlockName) +
                ", tkeyBlockSize=" + tkeyBlockSize +
                ", tkeyEntries=" + tkeyEntries +
                '}';
    }

}
