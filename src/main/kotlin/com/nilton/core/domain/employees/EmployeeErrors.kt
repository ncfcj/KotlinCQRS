package com.nilton.core.domain.employees

import com.nilton.core.domain.error.ErrorResponse
import io.ktor.http.*
import java.util.*

class EmployeeErrors {
    companion object {
        fun nameIsRequired() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "Employee name is required")
        }

        fun salaryShouldBePositive() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "Employee salary must be positive")
        }

        fun expectedWorkingHoursShouldBePositive() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest,  "Expected to have at least 1 hour")
        }

        fun employeeTypeIsRequired() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "Employee type is required")
        }

        fun employeeTypeIsNotInEnum() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "Employee type is not required")
        }

        fun employeeNotFound(id: UUID) : ErrorResponse {
            return ErrorResponse(HttpStatusCode.NotFound, "Employee with id $id was not found")
        }

        fun idIsRequired() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "Employee Id is required")
        }

        fun employeeAlreadyExistOnCompany(id : UUID, name: String, companyName: String) : ErrorResponse {
            return ErrorResponse(HttpStatusCode.Conflict, "Employee with id $id and name $name already exists for the company $companyName")
        }

        fun companyIdIsRequired() : ErrorResponse {
            return ErrorResponse(HttpStatusCode.BadRequest, "Employee Company Id is required")
        }
    }
}