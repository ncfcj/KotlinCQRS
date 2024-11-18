package com.nilton.app

import com.nilton.core.domain.error.ErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val response = ErrorResponse(
                HttpStatusCode.InternalServerError,
                "Erro interno do servidor: ${cause.localizedMessage}"
            )
            call.respond(HttpStatusCode.InternalServerError, response)
        }
    }
}