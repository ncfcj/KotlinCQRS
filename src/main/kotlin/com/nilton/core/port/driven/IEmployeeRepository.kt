package com.nilton.core.port.driven

import com.nilton.core.domain.employees.Employee
import java.util.UUID

interface IEmployeeRepository {
    fun listAll(): List<Employee>
    fun findById(id:UUID) : Employee?
    fun create(employee: Employee) : UUID
    fun update(employee: Employee)
    fun delete(employee: Employee)
    fun listByCompanyId(companyId:UUID) : List<Employee>
    fun findByNameAndCompanyId(id: UUID, name: String, companyId: UUID) : Employee?
}