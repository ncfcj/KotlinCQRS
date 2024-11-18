package com.nilton.core.port.driver.companies

import com.nilton.core.domain.companies.Company
import com.nilton.core.domain.error.ErrorResponse

interface IListCompanies {
    operator fun invoke() : ListCompaniesDto
}

sealed class ListCompaniesDto {
    data class Success(val listCompanies: List<Company>) : ListCompaniesDto()
    data class Failure(val error: ErrorResponse) : ListCompaniesDto()
}