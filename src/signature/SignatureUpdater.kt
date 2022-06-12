package signature

import java.nio.ByteBuffer
import java.security.Signature

class SignatureUpdater(val signature: Signature) {
    private val byteBuffer = ByteBuffer.allocate(10)

    fun update(value: String?) {
        if (value == null) {
            update(-1)
        } else {
            val bytes = value.toByteArray()
            update(bytes.size)
            signature.update(bytes)
        }
    }

    fun update(value: Int) {
        byteBuffer.clear()
        byteBuffer.putInt(value)
        signature.update(byteBuffer)
    }

    fun update(value: Long) {
        byteBuffer.clear()
        byteBuffer.putLong(value)
        signature.update(byteBuffer)
    }

    fun update(value: ByteArray) {
        signature.update(value)
    }

}