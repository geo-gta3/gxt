package ge.vakho.gxt.model;

import com.igormaznitsa.jbbp.io.JBBPOut;
import com.igormaznitsa.jbbp.mapper.Bin;
import ge.vakho.gxt.processor.WCharArrayFieldProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Bin
public class GXT {

    protected Block1 block1;
    protected Block2 block2;

    public Object newInstance(Class<?> klazz) {
        if (klazz == Block1.class) {
            return new Block1();
        }
        if (klazz == Block2.class) {
            return new Block2();
        }
        return null;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        JBBPOut.BeginBin(outputStream)
                .Bin(this, new WCharArrayFieldProcessor())
                .End();
    }

    public void writeTo(Path path) throws IOException {
        writeTo(Files.newOutputStream(path));
    }

    public Block1 getBlock1() {
        return block1;
    }

    public void setBlock1(Block1 block1) {
        this.block1 = block1;
    }

    public Block2 getBlock2() {
        return block2;
    }

    public void setBlock2(Block2 block2) {
        this.block2 = block2;
    }

}
