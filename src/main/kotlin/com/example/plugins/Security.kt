package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.backend.Auth.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.config.*

const val JWT_SECRET="eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkFsZXggUG9sb3poZW5jZXYiLCJpYXQiOjE1MTYyMzkwMjJ9"

fun Application.configureSecurity(config : TokenConfig, resource: HoconApplicationConfig) {
    authentication {
        jwt {
            realm = "ktor sample app"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
                validate { credential ->
                    if (credential.payload.audience.contains(config.audience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
