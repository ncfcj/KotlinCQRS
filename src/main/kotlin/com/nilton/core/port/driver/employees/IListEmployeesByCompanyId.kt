package com.nilton.core.port.driver.employees

import com.nilton.core.domain.employees.Employee
import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IListEmployeesByCompanyId {
    operator fun invoke(query : ListEmployeeByCompanyIdQuery) : ListEmployeeByCompanyIdDto
}

data class ListEmployeeByCompanyIdQuery(val id : UUID? = null)

sealed class ListEmployeeByCompanyIdDto {
    data class Success(val employeeList: List<Employee>) : ListEmployeeByCompanyIdDto()
    data class Failure(val error: ErrorResponse) : ListEmployeeByCompanyIdDto()
}