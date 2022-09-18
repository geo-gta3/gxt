package ge.vakho.gxt

import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * GXT consists of two blocks: [Block1] and [Block2] blocks. This class is responsible for parsing the GXT
 * information from a *.gxt file, using one of the overridden [from][.from] methods.
 *
 * This class also provides [toMap][.toMap] method to make working with GXT data easy. When you are done working
 * with the data, you may use [from][.from] method which accepts a [map][Map] object as an argument and
 * returns GXT object.
 *
 * To write GXT data to some file, or stream, use overridden [writeTo][.writeTo] methods.
 *
 * GTA III's GXT files are identical to those of GTA 2, except that they do not have headers – they start with the
 * TKEY block instead. There is also no special treatment of characters starting with 0x21 in TDAT entries.
 *
 * The Xbox version of Vice City uses this format as well.
 *
 * @see Block1
 * @see Block2
 */
data class GXT(var block1: Block1, var block2: Block2) {

    fun toMap(): Map<String, String> {
        val result = TreeMap<String, String>()
        var i = 0
        block1.entries.forEach { result[it.name] = block2.entries[i++].text }
        return result
    }

    @Throws(IOException::class)
    fun writeTo(file: File) {
        writeTo(file.toPath())
    }

    @Throws(IOException::class)
    fun writeTo(path: Path) {
        Files.newOutputStream(path).use { outputStream -> writeTo(outputStream) }
    }

    @Throws(IOException::class)
    fun writeTo(outputStream: OutputStream) {
        block1.entries.sort() // Sort TKEY entries before writing to file
        block1.writeTo(outputStream)
        block2.writeTo(outputStream)
    }

    companion object {
        @JvmStatic
        @Throws(IOException::class)
        fun from(path: Path): GXT {
            return from(path.toFile())
        }

        @JvmStatic
        @Throws(IOException::class)
        fun from(file: File): GXT {
            RandomAccessFile(file, "r").use { raf -> return from(raf) }
        }

        @JvmStatic
        @Throws(IOException::class)
        fun from(raf: RandomAccessFile): GXT {
            val block1 = Block1.from(raf)
            val block2 = Block2.from(raf, block1)
            return GXT(block1, block2)
        }

        @JvmStatic
        fun from(map: Map<String, String>): GXT {
            val block1 = Block1.from(map)
            val block2 = Block2.from(map.values)
            return GXT(block1, block2)
        }
    }
}