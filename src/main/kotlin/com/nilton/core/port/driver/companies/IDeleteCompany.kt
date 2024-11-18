package com.nilton.core.port.driver.companies

import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IDeleteCompany{
    operator fun invoke(command: DeleteCompanyCommand) : DeleteCompanyDto
}

data class DeleteCompanyCommand(
    val companyId : UUID? = null
)

sealed class DeleteCompanyDto{
    object Success : DeleteCompanyDto()
    data class Failure(val error: ErrorResponse) : DeleteCompanyDto()
}

