package ge.vakho.gxt.processor;

import com.igormaznitsa.jbbp.io.JBBPBitOutputStream;
import com.igormaznitsa.jbbp.io.JBBPCustomFieldWriter;
import com.igormaznitsa.jbbp.io.JBBPOut;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.JBBPMapperCustomFieldProcessor;
import com.igormaznitsa.jbbp.model.JBBPFieldString;
import com.igormaznitsa.jbbp.model.JBBPFieldStruct;

import java.io.IOException;
import java.lang.reflect.Field;

public class WCharArrayFieldProcessor implements JBBPMapperCustomFieldProcessor, JBBPCustomFieldWriter {

    @Override
    public Object prepareObjectForMapping(JBBPFieldStruct parsedBlock, Bin annotation, Field field) {
        return parsedBlock.findFieldForNameAndType("data", JBBPFieldString.class).getAsString();
    }

    @Override
    public void writeCustomField(JBBPOut context, JBBPBitOutputStream out, Object instanceForSaving,
                                 Field instanceCustomField, Bin fieldAnnotation, Object value) throws IOException {
        try {
            instanceCustomField.setAccessible(true); // Field may be protected
            char[] characters = ((String) instanceCustomField.get(instanceForSaving)).toCharArray();
            for (char character : characters) {
                out.writeShort(character, fieldAnnotation.byteOrder());
            }
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

}
