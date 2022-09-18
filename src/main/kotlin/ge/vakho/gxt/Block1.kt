package ge.vakho.gxt

import java.io.IOException
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * TKEY Block (also knows as Block 1) consists of the block information and [TKEY] entries.
 *
 * All [TKEY] entries are sorted in alphabetical order.
 *
 * @see TKEY
 */
class Block1(var entries: ArrayList<TKEY>) {

    @Throws(IOException::class)
    fun writeTo(outputStream: OutputStream) {
        // Write block name
        outputStream.write(BLOCK_NAME.map { it.code.toByte() }.toByteArray())

        // Write block size
        val bb4 = ByteBuffer.allocate(4) // 4 bytes buffer
        bb4.order(ByteOrder.LITTLE_ENDIAN)
        bb4.putInt(entries.size * 12) // SIZE * 12 bytes
        outputStream.write(bb4.array()) // Store in 4 bytes

        // Each entry is 12 bytes
        entries.forEach { it.writeTo(outputStream) }
    }

    companion object {
        const val BLOCK_NAME = "TKEY"

        @JvmStatic
        fun from(map: Map<String, String>): Block1 {
            // Set entries
            val tkeyEntries = ArrayList<TKEY>()
            var tdatOffset = 0u
            for ((key, value) in map) {
                tkeyEntries.add(TKEY(tdatOffset, key))
                tdatOffset += (value.length.toLong() * Character.BYTES + 2).toUInt() // Last two bytes is null symbol!
            }
            return Block1(tkeyEntries)
        }

        @JvmStatic
        @Throws(IOException::class)
        fun from(raf: RandomAccessFile): Block1 {
            raf.skipBytes(4) // Skip block name

            // Read block size
            val size = readBytes(raf, 4) { it.int }.toLong()

            // Read entries
            val entries = ArrayList<TKEY>()
            0.until(size / 12).forEach {
                entries.add(TKEY.from(raf))
            }
            return Block1(entries)
        }
    }
}