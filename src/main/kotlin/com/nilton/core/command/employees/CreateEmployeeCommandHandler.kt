package com.nilton.core.command.employees

import com.nilton.core.domain.employees.EmployeeErrors
import com.nilton.core.domain.employees.EmployeeFactory
import com.nilton.core.domain.employees.EmployeeType
import com.nilton.core.domain.error.ErrorResponse
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.CreateEmployeeCommand
import com.nilton.core.port.driver.employees.CreateEmployeeDto
import com.nilton.core.port.driver.employees.ICreateEmployee
import java.util.UUID

class CreateEmployeeCommandHandler(
    private val employeeRepository: IEmployeeRepository
) : ICreateEmployee
{
    override fun invoke(command: CreateEmployeeCommand): CreateEmployeeDto {
        val validationErrors = validateCommand(command)

        if (validationErrors.isNotEmpty()) {
            return CreateEmployeeDto.ValidationFailure(validationErrors)
        }

        val companyId = UUID.fromString(command.companyId.toString())

        val employee = EmployeeFactory.createEmployee(
            command.name,
            command.salaryByHour,
            command.expectedWorkingHours,
            companyId,
            command.type
        )

        val employeeId = employeeRepository.create(employee)

        return CreateEmployeeDto.Success(employeeId)
    }

    private fun validateCommand(command: CreateEmployeeCommand) : List<ErrorResponse> {
        val listOfValidationErrors = ArrayList<ErrorResponse>()

        if (command.name.isNullOrBlank())
            listOfValidationErrors.add(EmployeeErrors.nameIsRequired())

        if (command.salaryByHour == null || command.salaryByHour <= 0)
            listOfValidationErrors.add(EmployeeErrors.salaryShouldBePositive())

        if (command.expectedWorkingHours == null || command.expectedWorkingHours <= 0)
            listOfValidationErrors.add(EmployeeErrors.expectedWorkingHoursShouldBePositive())

        if (command.companyId == null)
            listOfValidationErrors.add(EmployeeErrors.companyIdIsRequired())

        if (command.type == null)
            listOfValidationErrors.add(EmployeeErrors.employeeTypeIsRequired())

        val typeIsInEnum = command.type != null && EmployeeType.entries.map { it.name }.contains(command.type.toString())

        if (!typeIsInEnum)
            listOfValidationErrors.add(EmployeeErrors.employeeTypeIsNotInEnum())

        return listOfValidationErrors
    }
}