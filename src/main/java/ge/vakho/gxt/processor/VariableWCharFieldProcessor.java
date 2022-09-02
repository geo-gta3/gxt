package ge.vakho.gxt.processor;

import com.igormaznitsa.jbbp.JBBPNamedNumericFieldMap;
import com.igormaznitsa.jbbp.JBBPVarFieldProcessor;
import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import com.igormaznitsa.jbbp.io.JBBPBitInputStream;
import com.igormaznitsa.jbbp.io.JBBPByteOrder;
import com.igormaznitsa.jbbp.model.JBBPAbstractArrayField;
import com.igormaznitsa.jbbp.model.JBBPAbstractField;
import com.igormaznitsa.jbbp.model.JBBPFieldString;

import java.io.IOException;

/**
 * This is a helper class that reads strings properly. Termination of string is at null symbol.
 */
public class VariableWCharFieldProcessor implements JBBPVarFieldProcessor {

    @Override
    public JBBPAbstractArrayField<? extends JBBPAbstractField> readVarArray(JBBPBitInputStream inStream, int arraySize, JBBPNamedFieldInfo fieldName, int extraValue, JBBPByteOrder byteOrder, JBBPNamedNumericFieldMap numericFieldMap) throws IOException {
        throw new IOException("This method must not be called");
    }

    @Override
    public JBBPAbstractField readVarField(JBBPBitInputStream inStream, JBBPNamedFieldInfo fieldName, int extraValue, JBBPByteOrder byteOrder, JBBPNamedNumericFieldMap numericFieldMap) throws IOException {
        int unsignedShort;
        StringBuilder sb = new StringBuilder();
        // Read until we encounter 0...
        while ((unsignedShort = inStream.readUnsignedShort(byteOrder)) != 0) {
            sb.append((char) unsignedShort);
        }
        sb.append((char) 0);
        return new JBBPFieldString(fieldName, sb.toString());
    }

}