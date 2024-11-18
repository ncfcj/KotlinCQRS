package com.nilton.core.port.driver.employees

import com.nilton.core.domain.error.ErrorResponse
import java.util.*

interface ICalculateEmployeeSalary {
    operator fun invoke(query: CalculateEmployeeSalaryQuery): CalculateEmployeeSalaryDto
}

data class CalculateEmployeeSalaryQuery(val id : UUID? = null)

sealed class CalculateEmployeeSalaryDto {
    data class Success(val salary: Double) : CalculateEmployeeSalaryDto()
    data class Failure(val error: ErrorResponse) : CalculateEmployeeSalaryDto()
}