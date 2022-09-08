package ge.vakho.gxt;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

import static ge.vakho.gxt.util.BufferUtils.readBytes;

/**
 * TDAT contains the value string that is an array of wide characters (2 bytes per character) to be displayed in the
 * game.
 *
 * <p>The array is terminated by a null character (0x0000). The location of the entry in absolute term is TDAT
 * entry offset + size of TKEY block + 22.</p>
 */
public class TDAT {

    protected String text;

    public TDAT() {
    }

    public TDAT(String text) {
        this.text = text;
    }

    public static TDAT from(RandomAccessFile raf) throws IOException {
        TDAT tdat = new TDAT();

        StringBuilder sb = new StringBuilder();
        while (true) {
            short shrt = readBytes(raf, 2, ByteBuffer::getShort);
            if (shrt == 0) {
                break;
            }
            sb.append((char) shrt);
        }
        tdat.text = sb.toString();

        return tdat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TDAT tdat = (TDAT) o;
        return Objects.equals(text, tdat.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "TDAT{" +
                "text='" + text + '\'' +
                '}';
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        ByteBuffer bb2 = ByteBuffer.allocate(2); // 2 bytes buffer
        bb2.order(ByteOrder.LITTLE_ENDIAN);

        for (char chr : text.toCharArray()) {
            bb2.putChar(chr);
            outputStream.write(bb2.array());
            bb2.putChar(0, (char) 0); // Clear buffer
            bb2.clear();
        }
        outputStream.write(new byte[2]); // Last two bytes is null symbol!
    }
}
