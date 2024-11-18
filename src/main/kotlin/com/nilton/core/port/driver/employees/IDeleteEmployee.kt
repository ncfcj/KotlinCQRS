package com.nilton.core.port.driver.employees

import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IDeleteEmployee {
    operator fun invoke(command: DeleteEmployeeCommand) : DeleteEmployeeDto
}

data class DeleteEmployeeCommand(
    val id : UUID? = null
)

sealed class DeleteEmployeeDto {
    object Success : DeleteEmployeeDto()
    data class Failure(val error: ErrorResponse) : DeleteEmployeeDto()
}