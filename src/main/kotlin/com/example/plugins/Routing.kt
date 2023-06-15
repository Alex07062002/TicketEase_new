package com.example.plugins

import com.example.backend.Auth.token.TokenConfig
import com.example.backend.Routing.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting(tokenConfig : TokenConfig) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        placeRoute()
        eventRoute()
        favoriteRoute()
        placeTimeRoute()
        ticketRoute()
        roomsRoute()
        buyerRoute(tokenConfig)
        organizerRoute(tokenConfig)
    }
}
