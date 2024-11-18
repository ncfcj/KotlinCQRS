package com.nilton.core.port.driver.companies

import com.nilton.core.domain.error.ErrorResponse
import java.util.UUID

interface IUpdateCompany {
    operator fun invoke(command: UpdateCompanyCommand) : UpdateCompanyDto
}

data class UpdateCompanyCommand(
    val name : String? = null,
    val id : UUID? = null
)

sealed class UpdateCompanyDto{
    object Success : UpdateCompanyDto()
    data class Failure(val error: ErrorResponse) : UpdateCompanyDto()
}