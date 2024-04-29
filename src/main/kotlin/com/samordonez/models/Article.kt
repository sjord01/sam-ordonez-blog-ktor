package com.samordonez.models

import org.jetbrains.exposed.sql.Table
import java.io.Serializable

data class Article(val id: Int, val title: String, val body: String): Serializable

object Articles : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}




/*
import com.samordonez.plugins.*
import io.ktor.server.application.*
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureTemplating()
    configureRouting()
}

class Article
private constructor(val id: Int, var title: String, var body: String) {
    companion object {
        private val idCounter = AtomicInteger()

        fun newEntry(title: String, body: String) = Article(idCounter.getAndIncrement(), title, body)
    }
}

val articles = mutableListOf(Article.newEntry(
    "The drive to develop!",
    "...it's what keeps me going."
))
 */