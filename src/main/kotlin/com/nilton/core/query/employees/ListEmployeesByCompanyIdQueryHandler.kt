package com.nilton.core.query.employees

import com.nilton.core.domain.companies.CompanyErrors
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.employees.ListEmployeeByCompanyIdDto
import com.nilton.core.port.driver.employees.ListEmployeeByCompanyIdQuery
import com.nilton.core.port.driver.employees.IListEmployeesByCompanyId

class ListEmployeesByCompanyIdQueryHandler(
    private val employeeRepository: IEmployeeRepository,
    private val companyRepository: ICompanyRepository
) : IListEmployeesByCompanyId
{
    override fun invoke(query: ListEmployeeByCompanyIdQuery): ListEmployeeByCompanyIdDto {
        if (query.id == null)
            return ListEmployeeByCompanyIdDto.Failure(CompanyErrors.idIsRequired())

        val company = companyRepository.findById(query.id)

        if (company == null)
            return ListEmployeeByCompanyIdDto.Failure(CompanyErrors.companyNotFound(query.id))

        val employeeList = employeeRepository.listByCompanyId(company.id)

        return ListEmployeeByCompanyIdDto.Success(employeeList)
    }
}