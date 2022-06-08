package signature

import java.nio.file.Path
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import kotlin.io.path.readText
import kotlin.io.path.writeText

const val BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----"
const val END_PUBLIC_KEY = "-----END PUBLIC KEY-----"
const val BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----"
const val END_PRIVATE_KEY = "-----END PRIVATE KEY-----"

private fun String.readSegment(begin: String, end: String) = this.substringAfter(begin).substringBefore(end)
private fun String.removeNewLines() = this.replace("""[\n\r]""".toRegex(), "")
private fun String.decodeBase64() = Base64.getDecoder().decode(this)
private fun ByteArray.encodeBase64() = Base64.getEncoder().encode(this)
fun decodePublicKey(string: String): PublicKey {
    val bytes = string.readSegment(BEGIN_PUBLIC_KEY, END_PUBLIC_KEY).removeNewLines().decodeBase64()
    val spec = PKCS8EncodedKeySpec(bytes)
    return KeyFactory.getInstance(spec.algorithm).generatePublic(spec)
}

fun encodePublicKey(key: PublicKey): String {
    return buildString {
        appendLine(BEGIN_PUBLIC_KEY)
        appendLine(key.encoded.encodeBase64())
        appendLine(END_PUBLIC_KEY)
    }
}

fun decodePrivateKey(string: String): PrivateKey {
    val bytes = string.readSegment(BEGIN_PRIVATE_KEY, END_PRIVATE_KEY).removeNewLines().decodeBase64()
    val spec = PKCS8EncodedKeySpec(bytes)
    return KeyFactory.getInstance(spec.algorithm).generatePrivate(spec)
}

fun encodePrivateKey(key: PrivateKey): String {
    return buildString {
        appendLine(BEGIN_PRIVATE_KEY)
        appendLine(key.encoded.encodeBase64())
        appendLine(END_PRIVATE_KEY)
    }
}

fun readPublicKey(path: Path): Result<PublicKey> = runCatching {
    decodePublicKey(path.readText())
}

fun writePublicKey(path: Path, key: PublicKey): Result<Unit> = runCatching {
    val str = encodePublicKey(key)
    path.writeText(str)
}

fun readPrivateKey(path: Path): Result<PrivateKey> = runCatching {
    decodePrivateKey(path.readText())
}

fun writePrivateKey(path: Path, key: PrivateKey): Result<Unit> = runCatching {
    val str = encodePrivateKey(key)
    path.writeText(str)
}