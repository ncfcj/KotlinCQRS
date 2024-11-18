package com.nilton.core.command.companies

import com.nilton.core.domain.companies.Company
import com.nilton.core.domain.companies.CompanyErrors
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driver.companies.ICreateCompany
import com.nilton.core.port.driver.companies.CreateCompanyCommand
import com.nilton.core.port.driver.companies.CreateCompanyDto
import java.util.*

class CreateCompanyCommandHandler(
    private val companyRepository: ICompanyRepository
) : ICreateCompany
{
    override fun invoke(command: CreateCompanyCommand): CreateCompanyDto {
        val companyName = command.name.toString()

        val company = Company(
            id = UUID.randomUUID(),
            name = companyName,
        )

        val isNameEmpty = command.name.isNullOrBlank()

        if (isNameEmpty)
            return CreateCompanyDto.Failure(CompanyErrors.nameIsRequired())

        val nameAlreadyExists = companyRepository.nameExists(companyName)

        if (nameAlreadyExists)
            return CreateCompanyDto.Failure(CompanyErrors.companyAlreadyExists(companyName))

        val companyId = companyRepository.create(company)

        return CreateCompanyDto.Success(companyId)
    }
}