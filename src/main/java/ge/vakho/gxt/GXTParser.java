package ge.vakho.gxt;

import com.igormaznitsa.jbbp.JBBPParser;
import ge.vakho.gxt.model.GXT;
import ge.vakho.gxt.processor.VariableWCharFieldProcessor;
import ge.vakho.gxt.processor.WCharArrayFieldProcessor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public final class GXTParser {

    private static JBBPParser INSTANCE;

    public static void reload() {
        try (
                InputStream inputStream = GXTParser.class.getResourceAsStream("/gxt.jbbp");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String expression = bufferedReader.lines().collect(Collectors.joining());
            INSTANCE = JBBPParser.prepare(expression);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized JBBPParser getInstance() {
        if (INSTANCE == null) {
            reload();
        }
        return INSTANCE;
    }

    public static GXT parse(Path path) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path)) {
            return GXTParser.getInstance()
                    .parse(inputStream, new VariableWCharFieldProcessor(), null)
                    .mapTo(new GXT(), new WCharArrayFieldProcessor());
        }
    }

    public static GXT parse(File file) throws IOException {
        return parse(file.toPath());
    }

}
