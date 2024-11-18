package com.nilton.core.domain.error

import io.ktor.http.*

data class ErrorResponse(val code: HttpStatusCode, val message: String)