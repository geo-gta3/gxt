package ge.vakho.gxt;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
class GXTTest {

    private static Path tmpGxtFile;
    private static Map<String, String> map;

    private static GXT gxt;

    @BeforeAll
    static void beforeAll() throws IOException {
        // Dummy temporary file for test methods
        tmpGxtFile = Files.createTempFile("gxt-", ".gxt");

        // Dummy map object for test methods
        map = new TreeMap<>();
        map.put("8001", "You failed miserably!!");
        map.put("ACCURA", "Accuracy");
        map.put("AEROPL", "Aeroplane");
        map.put("AIRPORT", "Francis Intl. Airport");
        map.put("ALEVEL", "Paramedic Mission Level ~1~");
        map.put("AM1", "'SAYONARA SALVATORE'");
    }

    @AfterAll
    static void afterAll() {
        try {
            Files.deleteIfExists(tmpGxtFile);
        } catch (IOException e) {
        }
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = "/from.csv")
    void fromMap(int index, String key, String value) throws IOException {
        gxt = GXT.from(map);
        assertEquals(key, gxt.block1.tkeyEntries.get(index).tdatEntryName);
        assertEquals(value, gxt.block2.tdatEntries.get(index).text);
        gxt.writeTo(tmpGxtFile); // Prepare dummy GXT file
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = "/from.csv")
    void fromPath(int index, String key, String value) throws IOException {
        GXT gxt = GXT.from(tmpGxtFile);
        assertEquals(key, gxt.block1.tkeyEntries.get(index).tdatEntryName);
        assertEquals(value, gxt.block2.tdatEntries.get(index).text);
    }

    @Order(3)
    @Test
    void toMap() {
        assertEquals(map, gxt.toMap());
    }

}