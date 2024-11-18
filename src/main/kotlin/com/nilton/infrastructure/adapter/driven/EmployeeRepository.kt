package com.nilton.infrastructure.adapter.driven

import com.nilton.core.domain.employees.*
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.infrastructure.database.Employees
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EmployeeRepository : IEmployeeRepository {
    override fun listAll(): List<Employee> {
        return transaction {
            Employees
                .selectAll()
                .map { row ->
                    EmployeeFactory.createEmployeeFromDto(
                        id = row[Employees.id],
                        name = row[Employees.name],
                        salaryByHour = row[Employees.salaryByHour],
                        expectedWorkingHours = row[Employees.expectedWorkingHours],
                        companyId = row[Employees.companyId],
                        type = row[Employees.type],
                    )
                }
        }
    }

    override fun findById(id: UUID): Employee? {
        return transaction {
            Employees
                .select { Employees.id eq id }
                .firstOrNull()
                ?.let { row ->
                    EmployeeFactory.createEmployeeFromDto(
                        id = row[Employees.id],
                        name = row[Employees.name],
                        salaryByHour = row[Employees.salaryByHour],
                        expectedWorkingHours = row[Employees.expectedWorkingHours],
                        companyId = row[Employees.companyId],
                        type = row[Employees.type],
                    )
                }
        }
    }

    override fun create(employee: Employee) : UUID {
        return transaction {
            Employees.insert {
                it[id] = employee.id
                it[name] = employee.name
                it[salaryByHour] = employee.salaryByHour
                it[expectedWorkingHours] = employee.expectedWorkingHours
                it[companyId] = employee.companyId
                it[type] = employee.type
            } get Employees.id
        }
    }

    override fun update(employee: Employee) {
        transaction {
            Employees.update({ Employees.id eq employee.id }) {
                it[name] = employee.name
                it[salaryByHour] = employee.salaryByHour
                it[expectedWorkingHours] = employee.expectedWorkingHours
                it[companyId] = employee.companyId
                it[type] = employee.type
            }
        }
    }

    override fun delete(employee: Employee) {
        transaction {
            Employees.deleteWhere { id eq employee.id }
        }
    }

    override fun listByCompanyId(companyId: UUID): List<Employee> {
        return transaction {
            Employees
                .select { Employees.companyId eq companyId }
                .map { row ->
                    EmployeeFactory.createEmployeeFromDto(
                        id = row[Employees.id],
                        name = row[Employees.name],
                        salaryByHour = row[Employees.salaryByHour],
                        expectedWorkingHours = row[Employees.expectedWorkingHours],
                        companyId = row[Employees.companyId],
                        type = row[Employees.type],
                    )
                }
        }
    }

    override fun findByNameAndCompanyId(id:UUID, name: String, companyId: UUID): Employee? {
        return transaction {
            Employees
                .select { (Employees.name eq name) and (Employees.companyId eq companyId) and (Employees.id neq id)}
                .firstOrNull()
                ?.let { row ->
                    EmployeeFactory.createEmployeeFromDto(
                        id = row[Employees.id],
                        name = row[Employees.name],
                        salaryByHour = row[Employees.salaryByHour],
                        expectedWorkingHours = row[Employees.expectedWorkingHours],
                        companyId = row[Employees.companyId],
                        type = row[Employees.type],
                    )
                }
        }
    }
}