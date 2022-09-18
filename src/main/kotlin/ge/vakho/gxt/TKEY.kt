package ge.vakho.gxt

import java.io.IOException
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * TKEY block contains [TDAT] entry offset and its name.
 *
 * TKEY block's size is always 12 (0xC) bytes.
 *
 * @see TDAT
 */
data class TKEY(var offset: UInt, var name: String) : Comparable<TKEY> {

    @Throws(IOException::class)
    fun writeTo(outputStream: OutputStream) {
        // Key offset (4 bytes)
        val bb4 = ByteBuffer.allocate(4)
        bb4.order(ByteOrder.LITTLE_ENDIAN)
        bb4.putInt(offset.toInt())
        outputStream.write(bb4.array())

        // Key (8 bytes)
        val bb8 = ByteBuffer.allocate(8)
        bb8.order(ByteOrder.LITTLE_ENDIAN)
        bb8.put(name.toByteArray())
        outputStream.write(bb8.array())
    }

    override fun compareTo(other: TKEY): Int {
        return name.compareTo(other.name)
    }

    companion object {
        @JvmStatic
        @Throws(IOException::class)
        fun from(raf: RandomAccessFile): TKEY {
            // Read offset
            val tdatEntryOffset = readBytes(raf, 4) { it.int }.toUInt()

            // Read name
            val tdatEntryName = buildString {
                val array = readBytes(raf, 8) { it.array() }
                var i = 0
                while (i < array.size && array[i] != 0.toByte()) {
                    append(array[i].toChar())
                    i++
                }
            }
            return TKEY(tdatEntryOffset, tdatEntryName);
        }
    }
}