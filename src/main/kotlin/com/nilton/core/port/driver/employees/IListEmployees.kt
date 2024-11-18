package com.nilton.core.port.driver.employees

import com.nilton.core.domain.employees.Employee

interface IListEmployees {
    operator fun invoke() : ListEmployeesDto
}

sealed class ListEmployeesDto {
    data class Success(val employees: List<Employee>) : ListEmployeesDto()
}