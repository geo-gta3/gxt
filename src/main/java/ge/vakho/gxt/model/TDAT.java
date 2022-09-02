package ge.vakho.gxt.model;

import com.igormaznitsa.jbbp.io.JBBPByteOrder;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

public class TDAT {

    @Bin(type = BinType.UNDEFINED, custom = true, byteOrder = JBBPByteOrder.LITTLE_ENDIAN)
    protected String data;

    public TDAT() {
    }

    public TDAT(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
