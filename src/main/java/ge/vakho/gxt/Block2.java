package ge.vakho.gxt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static ge.vakho.gxt.util.BufferUtils.readBytes;

/**
 * TDAT Block (also knows as Block 2) consists of the block information and {@link TDAT} entries.
 *
 * @see TDAT
 */
public class Block2 {

    protected final byte[] tdatBlockName = new byte[]{'T', 'D', 'A', 'T'};
    protected long tdatBlockSize;
    protected List<TDAT> tdatEntries;

    public static Block2 from(RandomAccessFile raf, Block1 block1) throws IOException {
        Block2 block2 = new Block2();

        raf.seek(raf.getFilePointer() + 4); // Skip TDAT string

        // Read TDAT block size
        block2.setTdatBlockSize((long) readBytes(raf, 4, ByteBuffer::getInt));

        // Read TDAT entries
        block2.tdatEntries = new ArrayList<>();
        for (TKEY tkey : block1.tkeyEntries) {

            // Calculate and goto (absolute) offset position
            long absoluteOffset = block1.tkeyBlockSize + tkey.tdatEntryOffset + 16;
            raf.seek(absoluteOffset);

            // Save TDAT entries
            block2.tdatEntries.add(TDAT.from(raf));
        }
        return block2;
    }

    public static Block2 from(Collection<String> values) {
        Block2 block2 = new Block2();
        block2.tdatEntries = new ArrayList<>();
        block2.tdatBlockSize = 0;
        for (String value : values) {
            block2.tdatEntries.add(new TDAT(value));
            block2.tdatBlockSize += (long) value.length() * Character.BYTES + 2; // Last two bytes is null symbol!
        }
        return block2;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        ByteBuffer bb4 = ByteBuffer.allocate(4); // 4 bytes buffer
        bb4.order(ByteOrder.LITTLE_ENDIAN);
        outputStream.write(tdatBlockName); // Block name
        bb4.putInt((int) tdatBlockSize); // Block size
        outputStream.write(bb4.array());

        for (TDAT tdat : tdatEntries) {
            tdat.writeTo(outputStream);
        }
    }

    public byte[] getTdatBlockName() {
        return tdatBlockName;
    }

    public long getTdatBlockSize() {
        return tdatBlockSize;
    }

    public void setTdatBlockSize(long tdatBlockSize) {
        this.tdatBlockSize = tdatBlockSize;
    }

    public List<TDAT> getTdatEntries() {
        return tdatEntries;
    }

    public void setTdatEntries(List<TDAT> tdatEntries) {
        this.tdatEntries = tdatEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block2 block2 = (Block2) o;
        return tdatBlockSize == block2.tdatBlockSize && Arrays.equals(tdatBlockName, block2.tdatBlockName) && Objects.equals(tdatEntries, block2.tdatEntries);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tdatBlockSize, tdatEntries);
        result = 31 * result + Arrays.hashCode(tdatBlockName);
        return result;
    }

    @Override
    public String toString() {
        return "Block2{" +
                "tdatBlockName=" + Arrays.toString(tdatBlockName) +
                ", tdatBlockSize=" + tdatBlockSize +
                ", tdatEntries=" + tdatEntries +
                '}';
    }

}
