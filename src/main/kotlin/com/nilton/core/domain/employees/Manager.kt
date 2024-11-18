package com.nilton.core.domain.employees

import java.util.*

class Manager(
    id : UUID,
    name : String,
    salaryByHour : Double,
    expectedWorkingHours : Int,
    companyId : UUID
) : Employee(id, name, salaryByHour, expectedWorkingHours, companyId, EmployeeType.Manager)
{
    override fun calculateSalary(): Double {
        val managerBonus = 1.5
        return super.calculateSalary() * managerBonus
    }

    override fun displayRole(): String {
        return "Manager"
    }
}