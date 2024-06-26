package com.samordonez.dao

import com.samordonez.models.*
import com.zaxxer.hikari.*
import io.ktor.server.config.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import java.io.*

object DatabaseSingleton {
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.storage.driverClassName").getString()
        val jdbcURL = config.property("ktor.storage.jdbcURL").getString() +
                (config.propertyOrNull("ktor.storage.dbFilePath")?.getString()?.let {
                    File(it).canonicalFile.absolutePath
                } ?: "")
        val database = Database.connect(createHikariDataSource(url = jdbcURL, driver = driverClassName))
        transaction(database) {
            SchemaUtils.create(Articles)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}