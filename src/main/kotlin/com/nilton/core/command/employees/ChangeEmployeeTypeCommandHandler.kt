package com.nilton.core.command.employees

import com.nilton.core.domain.employees.EmployeeErrors
import com.nilton.core.domain.employees.EmployeeType
import com.nilton.core.domain.error.ErrorResponse
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.ChangeEmployeeTypeCommand
import com.nilton.core.port.driver.employees.ChangeEmployeeTypeDto
import com.nilton.core.port.driver.employees.IChangeEmployeeType

class ChangeEmployeeTypeCommandHandler(
    private val employeeRepository: IEmployeeRepository
) : IChangeEmployeeType
{
    override fun invoke(command : ChangeEmployeeTypeCommand): ChangeEmployeeTypeDto {
        if (command.id == null)
            return ChangeEmployeeTypeDto.Failure(EmployeeErrors.idIsRequired())

        val employee = employeeRepository.findById(command.id)

        if (employee == null)
            return ChangeEmployeeTypeDto.Failure(EmployeeErrors.employeeNotFound(command.id))

        val validation = validateType(command.type)

        if (validation != null)
            return ChangeEmployeeTypeDto.Failure(validation)

        employee.changeType(command.type as EmployeeType)
        employeeRepository.update(employee)

        return ChangeEmployeeTypeDto.Success
    }

    private fun validateType(type : EmployeeType?) : ErrorResponse?{
        if (type == null)
            return EmployeeErrors.employeeTypeIsRequired()

        val typeIsInEnum = EmployeeType.entries.map { it.name }.contains(type.toString())

        if (!typeIsInEnum)
            return EmployeeErrors.employeeTypeIsNotInEnum()

        return null
    }
}