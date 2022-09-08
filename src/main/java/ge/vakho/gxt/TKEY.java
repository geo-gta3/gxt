package ge.vakho.gxt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

import static ge.vakho.gxt.util.BufferUtils.readBytes;

public class TKEY implements Comparable<TKEY> {

    protected long tdatEntryOffset;
    protected String tdatEntryName;

    public TKEY() {
    }

    public TKEY(long tdatEntryOffset, String tdatEntryName) {
        this.tdatEntryOffset = tdatEntryOffset;
        this.tdatEntryName = tdatEntryName;
    }

    public static TKEY from(RandomAccessFile raf) throws IOException {
        TKEY tkey = new TKEY();

        // Read TDAT entry offset
        tkey.setTdatEntryOffset((long) readBytes(raf, 4, ByteBuffer::getInt));

        // Read TDAT entry name
        byte[] array = readBytes(raf, 8, ByteBuffer::array);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length && array[i] != 0; i++) {
            sb.append((char) array[i]);
        }
        tkey.setTdatEntryName(sb.toString());

        return tkey;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        // Key offset (4 bytes)
        ByteBuffer bb4 = ByteBuffer.allocate(4);
        bb4.order(ByteOrder.LITTLE_ENDIAN);
        bb4.putInt((int) tdatEntryOffset);
        outputStream.write(bb4.array());

        // Key (8 bytes)
        ByteBuffer bb8 = ByteBuffer.allocate(8);
        bb8.order(ByteOrder.LITTLE_ENDIAN);
        bb8.put(tdatEntryName.getBytes());
        outputStream.write(bb8.array());
    }

    @Override
    public int compareTo(TKEY other) {
        return tdatEntryName.compareTo(other.tdatEntryName);
    }

    public long getTdatEntryOffset() {
        return tdatEntryOffset;
    }

    public void setTdatEntryOffset(long tdatEntryOffset) {
        this.tdatEntryOffset = tdatEntryOffset;
    }

    public String getTdatEntryName() {
        return tdatEntryName;
    }

    public void setTdatEntryName(String tdatEntryName) {
        this.tdatEntryName = tdatEntryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TKEY tkey = (TKEY) o;
        return tdatEntryOffset == tkey.tdatEntryOffset && Objects.equals(tdatEntryName, tkey.tdatEntryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tdatEntryOffset, tdatEntryName);
    }

    @Override
    public String toString() {
        return "TKEY{" +
                "tdatEntryOffset=" + tdatEntryOffset +
                ", tdatEntryName='" + tdatEntryName + '\'' +
                '}';
    }

}
