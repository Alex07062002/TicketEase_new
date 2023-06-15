package com.example

import com.example.plugins.*
import com.example.backend.Auth.token.TokenConfig
import com.example.backend.PostgreSQL.DatabaseFactory.DataBaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

val config = HoconApplicationConfig(ConfigFactory.load())

// total 1971 rows code
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    DataBaseFactory.init()
    val tokenConfig = TokenConfig(
        issuer = "http://127.0.0.1:8080",
        audience = "users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = JWT_SECRET

    )
    configureSecurity(tokenConfig, config)
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting(tokenConfig)
}
