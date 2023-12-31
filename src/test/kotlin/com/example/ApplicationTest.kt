package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.example.plugins.*
import com.example.Routing.TOKEN_CONFIG

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
