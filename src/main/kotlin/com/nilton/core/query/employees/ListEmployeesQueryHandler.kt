package com.nilton.core.query.employees

import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.IListEmployees
import com.nilton.core.port.driver.employees.ListEmployeesDto

class ListEmployeesQueryHandler(
    private val employeeRepository: IEmployeeRepository
) : IListEmployees
{
    override fun invoke(): ListEmployeesDto {
        val employeeList = employeeRepository.listAll()
        return ListEmployeesDto.Success(employeeList)
    }

}