package com.nilton.core.domain.employees

import java.util.UUID

sealed class EmployeeFactory {
    companion object {
        fun createEmployee(
            name : String?,
            salaryByHour : Double?,
            expectedWorkingHours : Int?,
            companyId : UUID,
            type : EmployeeType?
        ) : Employee
        {
            return when (type) {
                EmployeeType.Manager -> Manager(
                    id = UUID.randomUUID(),
                    name = name.toString(),
                    salaryByHour = salaryByHour ?: 0.0,
                    expectedWorkingHours = expectedWorkingHours ?: 0,
                    companyId = companyId,
                )

                EmployeeType.Developer -> Developer(
                    id = UUID.randomUUID(),
                    name = name.toString(),
                    salaryByHour = salaryByHour ?: 0.0,
                    expectedWorkingHours = expectedWorkingHours ?: 0,
                    companyId = companyId,
                )

                null -> Developer(
                    id = UUID.randomUUID(),
                    name = name.toString(),
                    salaryByHour = salaryByHour ?: 0.0,
                    expectedWorkingHours = expectedWorkingHours ?: 0,
                    companyId = companyId,
                )
            }
        }

        fun createEmployeeFromDto(
            id : UUID? = null,
            name : String?,
            salaryByHour : Double?,
            expectedWorkingHours : Int?,
            companyId : UUID,
            type : EmployeeType?
        ) : Employee
        {
            return when (type) {
                EmployeeType.Manager -> Manager(
                    id = id ?: UUID.randomUUID(),
                    name = name.toString(),
                    salaryByHour = salaryByHour ?: 0.0,
                    expectedWorkingHours = expectedWorkingHours ?: 0,
                    companyId = companyId,
                )

                EmployeeType.Developer -> Developer(
                    id = id ?: UUID.randomUUID(),
                    name = name.toString(),
                    salaryByHour = salaryByHour ?: 0.0,
                    expectedWorkingHours = expectedWorkingHours ?: 0,
                    companyId = companyId,
                )

                null -> Developer(
                    id = id ?: UUID.randomUUID(),
                    name = name.toString(),
                    salaryByHour = salaryByHour ?: 0.0,
                    expectedWorkingHours = expectedWorkingHours ?: 0,
                    companyId = companyId,
                )
            }
        }
    }
}