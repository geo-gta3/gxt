@file:JvmName("Utils")

package ge.vakho.gxt

import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.function.Function

@Throws(IOException::class)
fun <R> readBytes(raf: RandomAccessFile, byteSize: Int, function: Function<ByteBuffer, R>): R {
    val bb = ByteBuffer.allocate(byteSize)
    bb.order(ByteOrder.LITTLE_ENDIAN)
    return try {
        val bytes = ByteArray(byteSize)
        check(raf.read(bytes) != -1) { "$byteSize bytes not present" }
        bb.put(bytes)
        bb.rewind()
        function.apply(bb)
    } finally {
        bb.clear()
    }
}