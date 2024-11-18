package com.nilton.core.port.driver.employees

import com.nilton.core.domain.error.ErrorResponse
import java.util.*

interface IUpdateEmployee {
    operator fun invoke(command: UpdateEmployeeCommand) : UpdateEmployeeDto
}

data class UpdateEmployeeCommand(
    val id : UUID? = null,
    val name: String?,
    val salaryByHour : Double?,
    val expectedWorkingHours : Int?,
)

sealed class UpdateEmployeeDto {
    object Success : UpdateEmployeeDto()
    data class Failure(val error: ErrorResponse) : UpdateEmployeeDto()
    data class ValidationFailure(val error: List<ErrorResponse>) : UpdateEmployeeDto()
}