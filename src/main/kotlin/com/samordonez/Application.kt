package com.samordonez

import com.samordonez.plugins.*
import io.ktor.server.application.*
import com.samordonez.dao.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseSingleton.init(environment.config)
    configureRouting()
    configureTemplating()
}
