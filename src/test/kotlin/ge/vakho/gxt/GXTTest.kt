package ge.vakho.gxt

import ge.vakho.gxt.GXT.Companion.from
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class GXTTest {

    private lateinit var tmpGxtFile: Path
    private lateinit var map: TreeMap<String, String>
    private lateinit var gxt: GXT

    @BeforeAll
    internal fun beforeAll() {
        // Dummy temporary file for test methods
        tmpGxtFile = Files.createTempFile("gxt-", ".gxt")

        // Dummy map object for test methods
        map = TreeMap()
        map["8001"] = "You failed miserably!!"
        map["ACCURA"] = "Accuracy"
        map["AEROPL"] = "Aeroplane"
        map["AIRPORT"] = "Francis Intl. Airport"
        map["ALEVEL"] = "Paramedic Mission Level ~1~"
        map["AM1"] = "'SAYONARA SALVATORE'"
    }

    @AfterAll
    internal fun afterAll() {
        try {
            Files.deleteIfExists(tmpGxtFile)
        } catch (e: IOException) {
        }
    }

    @Order(1)
    @ParameterizedTest
    @CsvFileSource(resources = ["/from.csv"])
    fun fromMap(index: Int, key: String?, value: String?) {
        gxt = from(map)
        Assertions.assertEquals(key, gxt!!.block1.entries[index].name)
        Assertions.assertEquals(value, gxt!!.block2.entries[index].text)
        gxt!!.writeTo(tmpGxtFile!!) // Prepare dummy GXT file
    }

    @Order(2)
    @ParameterizedTest
    @CsvFileSource(resources = ["/from.csv"])
    fun fromPath(index: Int, key: String?, value: String?) {
        val (block1, block2) = from(tmpGxtFile!!)
        Assertions.assertEquals(key, block1.entries[index].name)
        Assertions.assertEquals(value, block2.entries[index].text)
    }

    @Order(3)
    @Test
    fun toMap() {
        Assertions.assertEquals(map, gxt.toMap())
    }
}