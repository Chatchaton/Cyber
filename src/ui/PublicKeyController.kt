package ui

import javafx.beans.property.SimpleMapProperty
import signature.readPublicKey
import tornadofx.*
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.*
import java.security.PublicKey
import kotlin.concurrent.thread
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isRegularFile
import kotlin.io.path.name


class PublicKeyController : Controller() {

    val keysDirectory = Path("public keys").apply { createDirectories() }

    val keysProperty = SimpleMapProperty<String, PublicKey>()

    val watcher = FileSystems.getDefault().newWatchService()
    val subscriptionKey = keysDirectory.register(
        watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY, OVERFLOW
    )

    init {
        initialize()
        thread(isDaemon = true) {
            while (true) {
                val key = watcher.take()

                val events = key.pollEvents()

                runLater {
                    if (events.any { it.kind() == OVERFLOW }) {
                        initialize()
                    } else {
                        events.forEach {
                            fun path() = keysDirectory.resolve(it.context() as Path)
                            fun add() {
                                pathToEntry(path())?.let { (name, key) ->
                                    keysProperty[name] = key
                                }
                            }

                            fun remove() {
                                keysProperty.remove(path().name)
                            }
                            when (it.kind()) {
                                ENTRY_CREATE -> add()
                                ENTRY_DELETE -> remove()
                                ENTRY_MODIFY -> {
                                    remove()
                                    add()
                                }
                            }
                        }
                    }
                }

                val valid = key.reset()
                if (!valid) break
            }
        }
    }

    fun initialize() {
        Files.newDirectoryStream(keysDirectory).use { stream ->
            stream.filter { it.isRegularFile() }.toList()
        }.flatMap { path ->
            pathToEntry(path)?.let { listOf(it) } ?: emptyList()
        }.toMap().let {
            keysProperty.set(it.toMutableMap().asObservable())
        }
    }

    fun pathToEntry(path: Path): Pair<String, PublicKey>? {
        return readPublicKey(path).getOrNull()?.let { path.name to it }
    }

}