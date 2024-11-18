package com.nilton.core.port.driver.employees

import com.nilton.core.domain.employees.EmployeeType
import com.nilton.core.domain.error.ErrorResponse
import java.util.*

interface ICreateEmployee {
    operator fun invoke(command : CreateEmployeeCommand) : CreateEmployeeDto
}

data class CreateEmployeeCommand(
    val name : String?,
    val salaryByHour : Double?,
    val expectedWorkingHours : Int?,
    val companyId : UUID?,
    val type : EmployeeType?
)

sealed class CreateEmployeeDto {
    data class Success(val id : UUID) : CreateEmployeeDto()
    data class Failure(val error : ErrorResponse) : CreateEmployeeDto()
    data class ValidationFailure(val error: List<ErrorResponse>) : CreateEmployeeDto()
}