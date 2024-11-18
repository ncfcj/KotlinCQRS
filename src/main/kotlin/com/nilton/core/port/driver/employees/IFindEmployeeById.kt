package com.nilton.core.port.driver.employees

import com.nilton.core.domain.employees.Employee
import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IFindEmployeeById {
    operator fun invoke(query : FindEmployeeByIdQuery) : FindEmployeeByIdDto
}

data class FindEmployeeByIdQuery(
    val id : UUID? = null
)

sealed class FindEmployeeByIdDto{
    data class Success(val employee: Employee) : FindEmployeeByIdDto()
    data class Failure(val error: ErrorResponse) : FindEmployeeByIdDto()
}