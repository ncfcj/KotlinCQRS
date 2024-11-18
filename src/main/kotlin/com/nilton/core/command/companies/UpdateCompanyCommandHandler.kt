package com.nilton.core.command.companies

import com.nilton.core.domain.companies.CompanyErrors
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driver.companies.IUpdateCompany
import com.nilton.core.port.driver.companies.UpdateCompanyCommand
import com.nilton.core.port.driver.companies.UpdateCompanyDto

class UpdateCompanyCommandHandler(
    private val companyRepository: ICompanyRepository
) : IUpdateCompany
{
    override fun invoke(command: UpdateCompanyCommand) : UpdateCompanyDto{
        if (command.id == null)
            return UpdateCompanyDto.Failure(CompanyErrors.idIsRequired())

        val company = companyRepository.findById(command.id)

        if (company == null)
            return UpdateCompanyDto.Failure(CompanyErrors.companyNotFound(command.id))

        val companyName = command.name.toString()

        val isNameEmpty = command.name.isNullOrBlank()

        if (isNameEmpty)
            return UpdateCompanyDto.Failure(CompanyErrors.nameIsRequired())

        val nameAlreadyExists = companyRepository.nameExists(companyName)

        if (nameAlreadyExists)
            return UpdateCompanyDto.Failure(CompanyErrors.companyAlreadyExists(companyName))

        companyRepository.update(company)

        return UpdateCompanyDto.Success
    }
}