package com.nilton.core.domain.employees

import java.util.*

class Developer(
    id: UUID,
    name: String,
    salaryByHour : Double,
    expectedWorkingHours : Int,
    companyId : UUID
) : Employee(id, name, salaryByHour, expectedWorkingHours, companyId, EmployeeType.Developer)
{
    override fun calculateSalary(): Double {
        val developerBonus = 1.25
        val bonusByProject = 500

        return (super.calculateSalary() * developerBonus) + bonusByProject
    }

    override fun displayRole(): String {
        return "Developer"
    }
}