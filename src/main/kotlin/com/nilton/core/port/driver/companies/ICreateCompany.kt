package com.nilton.core.port.driver.companies

import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface ICreateCompany {
    operator fun invoke(command: CreateCompanyCommand) : CreateCompanyDto
}

data class CreateCompanyCommand(
    var name: String? = null,
)

sealed class CreateCompanyDto{
    data class Success(val companyId: UUID) : CreateCompanyDto()
    data class Failure(val error : ErrorResponse) : CreateCompanyDto()
}
