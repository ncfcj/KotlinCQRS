package com.nilton.core.command.companies

import com.nilton.core.domain.companies.CompanyErrors
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driver.companies.DeleteCompanyCommand
import com.nilton.core.port.driver.companies.DeleteCompanyDto
import com.nilton.core.port.driver.companies.IDeleteCompany

class DeleteCompanyCommandHandler(
    private val companyRepository: ICompanyRepository
) : IDeleteCompany
{
    override fun invoke(command: DeleteCompanyCommand): DeleteCompanyDto {
        if (command.companyId == null)
            return DeleteCompanyDto.Failure(CompanyErrors.idIsRequired())

        val company = companyRepository.findById(command.companyId)

        if (company == null)
            return DeleteCompanyDto.Failure(CompanyErrors.companyNotFound(command.companyId))

        companyRepository.delete(company)

        return DeleteCompanyDto.Success
    }
}