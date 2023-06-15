package com.example.backend.Auth.token

interface TokenService {
    fun generate(
        config : TokenConfig,
        vararg claim: TokenClaim
    ) : String
}