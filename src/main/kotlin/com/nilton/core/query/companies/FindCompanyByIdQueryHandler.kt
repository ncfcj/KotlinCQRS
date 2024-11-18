package com.nilton.core.query.companies

import com.nilton.core.domain.companies.CompanyErrors
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driver.companies.FindCompanyByIdDto
import com.nilton.core.port.driver.companies.FindCompanyByIdQuery
import com.nilton.core.port.driver.companies.IFindCompanyById

class FindCompanyByIdQueryHandler(
    private val companyRepository: ICompanyRepository
) : IFindCompanyById
{
    override fun invoke(query: FindCompanyByIdQuery): FindCompanyByIdDto {
        if (query.id == null)
            return FindCompanyByIdDto.Failure(CompanyErrors.idIsRequired())

        val company = companyRepository.findById(query.id)

        if (company == null)
            return FindCompanyByIdDto.Failure(CompanyErrors.companyNotFound(query.id))

        return FindCompanyByIdDto.Success(company)
    }

}