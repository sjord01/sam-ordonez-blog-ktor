package com.samordonez.plugins

import com.samordonez.dao.DAOFacade
import com.samordonez.dao.DAOFacadeCacheImpl
import com.samordonez.dao.DAOFacadeImpl
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import java.io.File

fun Application.configureRouting() {
    val dao: DAOFacade = DAOFacadeCacheImpl(
        DAOFacadeImpl(),
        File(environment.config.property("storage.ehcacheFilePath").getString())
    ).apply {
        runBlocking {
            if(allArticles().isEmpty()) {
                addNewArticle("The drive to develop!", "...it's what keeps me going.")
            }
        }
    }
}
