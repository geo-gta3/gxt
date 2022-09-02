package ge.vakho.gxt.model;

import com.igormaznitsa.jbbp.io.JBBPByteOrder;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

public class TKEY implements Comparable<TKEY> {

    @Bin(order = 1, type = BinType.UINT, byteOrder = JBBPByteOrder.LITTLE_ENDIAN)
    protected long tdatEntryOffset;

    @Bin(order = 2, type = BinType.BYTE_ARRAY, byteOrder = JBBPByteOrder.LITTLE_ENDIAN)
    protected byte[] tdatEntryName;

    public TKEY() {
    }

    public TKEY(long tdatEntryOffset, String tdatEntryName) {
        this.tdatEntryOffset = tdatEntryOffset;
        this.tdatEntryName = tdatEntryName.getBytes();
    }

    public TKEY(long tdatEntryOffset, byte[] tdatEntryName) {
        this.tdatEntryOffset = tdatEntryOffset;
        this.tdatEntryName = tdatEntryName;
    }

    public long getTdatEntryOffset() {
        return tdatEntryOffset;
    }

    public void setTdatEntryOffset(long tdatEntryOffset) {
        this.tdatEntryOffset = tdatEntryOffset;
    }

    public byte[] getTdatEntryName() {
        return tdatEntryName;
    }

    public void setTdatEntryName(byte[] tdatEntryName) {
        this.tdatEntryName = tdatEntryName;
    }

    @Override
    public int compareTo(TKEY other) {
        return new String(tdatEntryName).compareTo(new String(other.tdatEntryName));
    }

}
