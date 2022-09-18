package ge.vakho.gxt

import ge.vakho.gxt.TDAT.Companion.from
import java.io.IOException
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * TDAT Block (also knows as Block 2) consists of the block information and [TDAT] entries.
 *
 * @see TDAT
 */
data class Block2(var size: UInt, var entries: MutableList<TDAT>) {

    @Throws(IOException::class)
    fun writeTo(outputStream: OutputStream) {
        // Write block name
        outputStream.write(BLOCK_NAME.map { it.code.toByte() }.toByteArray())

        // Write block size
        val bb4 = ByteBuffer.allocate(4) // 4 bytes buffer
        bb4.order(ByteOrder.LITTLE_ENDIAN)
        bb4.putInt(size.toInt())
        outputStream.write(bb4.array())

        entries.forEach { it.writeTo(outputStream) }
    }

    companion object {
        const val BLOCK_NAME = "TDAT";

        @JvmStatic
        @Throws(IOException::class)
        fun from(raf: RandomAccessFile, block1: Block1): Block2 {
            raf.skipBytes(4) // Skip block name

            // Read block size
            val size = readBytes(raf, 4, { it.int }).toUInt()

            // Read entries
            val entries = ArrayList<TDAT>().apply {
                block1.entries.forEach { tkey ->
                    // Calculate and goto (absolute) offset position
                    val absoluteOffset = (block1.entries.size * 12) + tkey.offset.toLong() + 16
                    raf.seek(absoluteOffset)

                    // Save TDAT entries
                    add(from(raf))
                }
            }
            return Block2(size, entries)
        }

        @JvmStatic
        fun from(values: Collection<String>): Block2 {
            val entries = ArrayList<TDAT>(values.size)
            var size = 0u
            values.forEach {
                entries.add(TDAT(it))
                size += (it.length.toLong() * Character.BYTES + 2).toUInt() // Last two bytes is null symbol!
            }
            return Block2(size, entries)
        }
    }
}