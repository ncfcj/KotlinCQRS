package com.nilton.core.domain.companies

import com.nilton.core.domain.error.ErrorResponse
import io.ktor.http.*
import java.util.UUID

class CompanyErrors {
    companion object {
        fun idIsRequired() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "The company id is required")
        }

        fun nameIsRequired() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "The company name is required")
        }

        fun companyAlreadyExists(companyName : String) : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "The company name $companyName already exists")
        }

        fun companyNotFound(id : UUID) : ErrorResponse {
            return ErrorResponse(HttpStatusCode.NotFound, "The company with id $id was not found")
        }
    }
}