package com.nilton.core.port.driver.companies

import com.nilton.core.domain.companies.Company
import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IFindCompanyById {
    operator fun invoke(query: FindCompanyByIdQuery) : FindCompanyByIdDto
}

data class FindCompanyByIdQuery(
    val id: UUID? = null
)

sealed class FindCompanyByIdDto {
    data class Success(val company: Company) : FindCompanyByIdDto()
    data class Failure(val error: ErrorResponse) : FindCompanyByIdDto()
}