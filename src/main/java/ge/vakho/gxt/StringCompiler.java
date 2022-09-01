package ge.vakho.gxt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StringCompiler {

    private Map<String, Short> converterJsonMap;
    public TableData mainTable = new TableData();

    public void addTextLine(String key, String text) {
        long offset = mainTable.getData().size() * Character.BYTES;
        mainTable.getKeys().add(new TableEntry(key, offset));
        convertString(text, mainTable.getData());
    }

    public void convertString(String text, List<Short> buf) {
        for (int i = 0; i < text.toCharArray().length; i++) {
            buf.add((short) text.charAt(i));
        }
        buf.add((short) 0);
    }

    public void outputIntoStream(OutputStream s) throws IOException {
        if (isMainTableNeedSorting()) {
            Collections.sort(mainTable.getKeys());
        }
        System.out.printf("Table MAIN has %d keys\n", mainTable.getKeys().size());
        mainTable.writeToStream(s);
    }

    public boolean isMainTableNeedSorting() {
        return true;
    }

}
