package com.nilton.core.port.driver.employees

import com.nilton.core.domain.employees.EmployeeType
import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IChangeEmployeeType {
    operator fun invoke(command : ChangeEmployeeTypeCommand) : ChangeEmployeeTypeDto
}

data class ChangeEmployeeTypeCommand (
    val id : UUID? = null,
    val type : EmployeeType? = null,
)

sealed class ChangeEmployeeTypeDto {
    object Success : ChangeEmployeeTypeDto()
    data class Failure(val error: ErrorResponse) : ChangeEmployeeTypeDto()
}