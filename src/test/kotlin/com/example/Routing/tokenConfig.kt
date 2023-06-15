package com.example.Routing

import com.example.backend.Auth.token.TokenConfig
import com.example.config
import com.example.plugins.JWT_SECRET

    val TOKEN_CONFIG = TokenConfig(
        issuer = config.property("jwt.issuer").getString(),
        audience = config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = JWT_SECRET
    )
