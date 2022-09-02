package ge.vakho.gxt.model;

import com.igormaznitsa.jbbp.io.JBBPByteOrder;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

@Bin
public class Block2 {

    @Bin(order = 1, type = BinType.BYTE_ARRAY)
    protected String tdatBlockName;

    @Bin(order = 2, type = BinType.UINT, byteOrder = JBBPByteOrder.LITTLE_ENDIAN)
    protected long tdatBlockSize;

    @Bin(order = 3)
    protected TDAT[] tdatEntries;

    public String getTdatBlockName() {
        return tdatBlockName;
    }

    public void setTdatBlockName(String tdatBlockName) {
        this.tdatBlockName = tdatBlockName;
    }

    public long getTdatBlockSize() {
        return tdatBlockSize;
    }

    public void setTdatBlockSize(long tdatBlockSize) {
        this.tdatBlockSize = tdatBlockSize;
    }

    public TDAT[] getTdatEntries() {
        return tdatEntries;
    }

    public void setTdatEntries(TDAT[] tdatEntries) {
        this.tdatEntries = tdatEntries;
    }

    public Object newInstance(Class<?> klazz) {
        return klazz == TDAT.class ? new TDAT() : null;
    }

}
