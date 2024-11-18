package com.nilton.core.domain.employees

import java.util.UUID

abstract class Employee (
    val id : UUID,
    var name: String,
    var salaryByHour: Double,
    var expectedWorkingHours : Int,
    var companyId: UUID,
    var type: EmployeeType
)
{
    open fun calculateSalary() : Double {
        if (salaryByHour <= 0 || expectedWorkingHours <= 0)
            return 0.0

        return salaryByHour * expectedWorkingHours
    }

    open fun updateMainData(
        name : String?,
        salaryByHour : Double?,
        expectedWorkingHours : Int?,
    )
    {
        this.name = name ?: this.name
        this.salaryByHour = salaryByHour ?: this.salaryByHour
        this.expectedWorkingHours = expectedWorkingHours ?: this.expectedWorkingHours
    }

    open fun changeType(type : EmployeeType){
        this.type = type
    }

    abstract fun displayRole() : String
}