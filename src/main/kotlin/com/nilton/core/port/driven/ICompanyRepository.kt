package com.nilton.core.port.driven

import com.nilton.core.domain.companies.Company
import java.util.UUID

interface ICompanyRepository {
    fun findAll(): List<Company>
    fun findById(companyId: UUID): Company?
    fun nameExists(name: String): Boolean
    fun create(company: Company): UUID
    fun update(company: Company)
    fun delete(company: Company)
}