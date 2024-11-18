package com.nilton.infrastructure.adapter.driven

import com.nilton.core.domain.companies.Company
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.infrastructure.database.Companies
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class CompanyRepository : ICompanyRepository {
    override fun findAll(): List<Company> {
        return transaction {
            Companies
                .selectAll()
                .map {
                    Company(
                        id = it[Companies.id],
                        name = it[Companies.name],
                    )
                }
        }
    }

    override fun findById(companyId: UUID): Company? {
        return transaction {
            Companies
                .select { Companies.id eq companyId }
                .singleOrNull()
                ?.let {
                    Company(
                        id = it[Companies.id],
                        name = it[Companies.name],
                    )
                }
        }
    }

    override fun nameExists(name: String): Boolean {
        return transaction{
            Companies
                .select { Companies.name eq name }
                .any()
        }
    }

    override fun create(company: Company): UUID {
        return transaction {
            Companies.insert {
                it[id] = UUID.randomUUID()
                it[name] = company.name
            } get Companies.id
        }
    }

    override fun update(company: Company) {
        transaction {
            Companies.update({ Companies.id eq company.id })
            {
                it[name] = company.name
            }
        }
    }

    override fun delete(company: Company) {
        transaction {
            Companies.deleteWhere { id eq company.id }
        }
    }
}