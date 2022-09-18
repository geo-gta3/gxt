package ge.vakho.gxt

import java.io.IOException
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * TDAT contains the value string that is an array of wide characters (2 bytes per character) to be displayed in the
 * game.
 *
 * The array is terminated by a null character (0x0000). The location of the entry in absolute term is TDAT
 * entry offset + size of TKEY block + 22.
 */
data class TDAT(var text: String) {

    @Throws(IOException::class)
    fun writeTo(outputStream: OutputStream) {
        val bb2 = ByteBuffer.allocate(2) // 2 bytes buffer
        bb2.order(ByteOrder.LITTLE_ENDIAN)
        for (c in text) {
            bb2.putChar(c)
            outputStream.write(bb2.array())
            bb2.putChar(0, 0.toChar()) // Clear buffer
            bb2.clear()
        }
        outputStream.write(ByteArray(2)) // Last two bytes is null symbol!
    }

    companion object {
        @JvmStatic
        @Throws(IOException::class)
        fun from(raf: RandomAccessFile): TDAT {
            val text = buildString {
                while (true) {
                    val shrt = readBytes(raf, 2) { it.short }
                    if (shrt.toInt() == 0) {
                        break
                    }
                    append(Char(shrt.toUShort()))
                }
            }
            return TDAT(text)
        }
    }
}