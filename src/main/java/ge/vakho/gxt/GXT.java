package ge.vakho.gxt;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class GXT {

    protected Block1 block1;
    protected Block2 block2;

    public static GXT from(Path path) throws IOException {
        return from(path.toFile());
    }

    public static GXT from(File file) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            return from(raf);
        }
    }

    public static GXT from(RandomAccessFile raf) throws IOException {
        GXT gxt = new GXT();
        gxt.setBlock1(Block1.from(raf));                // Block 1 (TKEY)
        gxt.setBlock2(Block2.from(raf, gxt.block1));    // Block 2 (TDAT)
        return gxt;
    }

    public static GXT from(Map<String, String> map) {
        GXT gxt = new GXT();
        gxt.block1 = Block1.from(map);          // Block 1 (TKEY)
        gxt.block2 = Block2.from(map.values()); // Block 2 (TDAT)
        return gxt;
    }

    public Map<String, String> toMap() {
        Map<String, String> result = new TreeMap<>();
        for (int i = 0; i < block1.tkeyEntries.size(); i++) {
            final TKEY tkey = block1.tkeyEntries.get(i);
            final TDAT tdat = block2.tdatEntries.get(i);
            result.put(tkey.tdatEntryName, tdat.text);
        }
        return result;
    }

    public void writeTo(File file) throws IOException {
        writeTo(file.toPath());
    }

    public void writeTo(Path path) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            writeTo(outputStream);
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        block1.writeTo(outputStream);
        block2.writeTo(outputStream);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GXT gxt = (GXT) o;
        return Objects.equals(block1, gxt.block1) && Objects.equals(block2, gxt.block2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block1, block2);
    }

    @Override
    public String toString() {
        return "GXT{" +
                "block1=" + block1 +
                ", block2=" + block2 +
                '}';
    }

}
