package com.nilton.core.query.companies

import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driver.companies.IListCompanies
import com.nilton.core.port.driver.companies.ListCompaniesDto

class ListCompaniesQueryHandler(
    private val companyRepository: ICompanyRepository
) : IListCompanies
{
    override fun invoke(): ListCompaniesDto {
        val companies = companyRepository.findAll()
        return ListCompaniesDto.Success(companies)
    }

}