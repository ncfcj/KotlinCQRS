package com.nilton.core.command.employees

import com.nilton.core.domain.employees.EmployeeErrors
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.DeleteEmployeeCommand
import com.nilton.core.port.driver.employees.DeleteEmployeeDto
import com.nilton.core.port.driver.employees.IDeleteEmployee

class DeleteEmployeeCommandHandler(
    private val employeeRepository: IEmployeeRepository
) : IDeleteEmployee
{
    override fun invoke(command: DeleteEmployeeCommand): DeleteEmployeeDto {
        if (command.id == null)
            return DeleteEmployeeDto.Failure(EmployeeErrors.idIsRequired())

        val employee = employeeRepository.findById(command.id)

        if (employee == null)
            return DeleteEmployeeDto.Failure(EmployeeErrors.employeeNotFound(command.id))

        employeeRepository.delete(employee)

        return DeleteEmployeeDto.Success
    }

}