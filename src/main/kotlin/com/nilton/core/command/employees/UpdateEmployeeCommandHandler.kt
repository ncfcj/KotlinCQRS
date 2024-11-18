package com.nilton.core.command.employees

import com.nilton.core.domain.companies.CompanyErrors
import com.nilton.core.domain.employees.EmployeeErrors
import com.nilton.core.domain.error.ErrorResponse
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.IUpdateEmployee
import com.nilton.core.port.driver.employees.UpdateEmployeeCommand
import com.nilton.core.port.driver.employees.UpdateEmployeeDto

class UpdateEmployeeCommandHandler(
    private val employeeRepository: IEmployeeRepository,
    private val companyRepository: ICompanyRepository
) : IUpdateEmployee
{
    override fun invoke(command: UpdateEmployeeCommand) : UpdateEmployeeDto {
        if (command.id == null)
            return UpdateEmployeeDto.Failure(EmployeeErrors.idIsRequired())

        val employee = employeeRepository.findById(command.id)

        if (employee == null)
            return UpdateEmployeeDto.Failure(EmployeeErrors.employeeNotFound(command.id))

        val validationErrors = validateCommand(command)

        if (validationErrors.isNotEmpty())
            return UpdateEmployeeDto.ValidationFailure(validationErrors)

        val company = companyRepository.findById(employee.companyId)

        if (company == null)
            return UpdateEmployeeDto.Failure(CompanyErrors.companyNotFound(employee.companyId))

        val employeeFound = employeeRepository.findByNameAndCompanyId(employee.id, command.name.toString(), employee.companyId)

        if (employeeFound != null)
            return UpdateEmployeeDto.Failure(EmployeeErrors.employeeAlreadyExistOnCompany(employeeFound.id, employeeFound.name, company.name))

        employee.updateMainData(
            command.name,
            command.salaryByHour,
            command.expectedWorkingHours
        )

        employeeRepository.update(employee)

        return UpdateEmployeeDto.Success
    }

    private fun validateCommand(command: UpdateEmployeeCommand) : List<ErrorResponse> {
        val listOfValidationErrors = ArrayList<ErrorResponse>()

        if (command.name.isNullOrBlank())
            listOfValidationErrors.add(EmployeeErrors.nameIsRequired())

        if (command.salaryByHour == null || command.salaryByHour <= 0)
            listOfValidationErrors.add(EmployeeErrors.salaryShouldBePositive())

        if (command.expectedWorkingHours == null || command.expectedWorkingHours <= 0)
            listOfValidationErrors.add(EmployeeErrors.expectedWorkingHoursShouldBePositive())

        return listOfValidationErrors
    }
}