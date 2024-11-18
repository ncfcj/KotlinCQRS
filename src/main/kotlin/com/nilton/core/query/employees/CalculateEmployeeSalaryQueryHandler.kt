package com.nilton.core.query.employees

import com.nilton.core.domain.employees.EmployeeErrors
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.CalculateEmployeeSalaryDto
import com.nilton.core.port.driver.employees.CalculateEmployeeSalaryQuery
import com.nilton.core.port.driver.employees.ICalculateEmployeeSalary


class CalculateEmployeeSalaryQueryHandler(
    private val employeeRepository: IEmployeeRepository
) : ICalculateEmployeeSalary
{
    override fun invoke(query: CalculateEmployeeSalaryQuery): CalculateEmployeeSalaryDto {
        if (query.id == null)
            return CalculateEmployeeSalaryDto.Failure(EmployeeErrors.idIsRequired())

        val employee = employeeRepository.findById(query.id)

        if (employee == null)
            return CalculateEmployeeSalaryDto.Failure(EmployeeErrors.employeeNotFound(query.id))

        val salary = employee.calculateSalary()

        return CalculateEmployeeSalaryDto.Success(salary)
    }
}