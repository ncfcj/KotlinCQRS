package com.nilton.core.query.employees

import com.nilton.core.domain.employees.EmployeeErrors
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.FindEmployeeByIdDto
import com.nilton.core.port.driver.employees.FindEmployeeByIdQuery
import com.nilton.core.port.driver.employees.IFindEmployeeById

class FindEmployeeByIdQueryHandler(
    private val employeeRepository: IEmployeeRepository
) : IFindEmployeeById
{
    override fun invoke(query: FindEmployeeByIdQuery): FindEmployeeByIdDto {
        if (query.id == null)
            return FindEmployeeByIdDto.Failure(EmployeeErrors.idIsRequired())

        val employee = employeeRepository.findById(query.id)

        if (employee == null)
            return FindEmployeeByIdDto.Failure(EmployeeErrors.employeeNotFound(query.id))

        return FindEmployeeByIdDto.Success(employee)
    }

}