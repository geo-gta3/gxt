package ge.vakho.gxt.model;

import com.igormaznitsa.jbbp.io.JBBPByteOrder;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

@Bin
public class Block1 {

    @Bin(type = BinType.BYTE_ARRAY)
    protected String tkeyBlockName;

    @Bin(type = BinType.UINT, byteOrder = JBBPByteOrder.LITTLE_ENDIAN)
    protected long tkeyBlockSize;

    protected TKEY[] tkeyEntries;

    public String getTkeyBlockName() {
        return tkeyBlockName;
    }

    public void setTkeyBlockName(String tkeyBlockName) {
        this.tkeyBlockName = tkeyBlockName;
    }

    public long getTkeyBlockSize() {
        return tkeyBlockSize;
    }

    public void setTkeyBlockSize(long tkeyBlockSize) {
        this.tkeyBlockSize = tkeyBlockSize;
    }

    public TKEY[] getTkeyEntries() {
        return tkeyEntries;
    }

    public void setTkeyEntries(TKEY[] tkeyEntries) {
        this.tkeyEntries = tkeyEntries;
    }

    public Object newInstance(Class<?> klazz) {
        return klazz == TKEY.class ? new TKEY() : null;
    }

}
