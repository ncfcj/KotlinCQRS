package com.nilton.app

import com.nilton.core.command.companies.CreateCompanyCommandHandler
import com.nilton.core.command.companies.DeleteCompanyCommandHandler
import com.nilton.core.command.companies.UpdateCompanyCommandHandler
import com.nilton.core.command.employees.ChangeEmployeeTypeCommandHandler
import com.nilton.core.command.employees.CreateEmployeeCommandHandler
import com.nilton.core.command.employees.DeleteEmployeeCommandHandler
import com.nilton.core.command.employees.UpdateEmployeeCommandHandler
import com.nilton.core.port.driven.ICompanyRepository
import com.nilton.core.port.driven.IEmployeeRepository
import com.nilton.core.port.driver.companies.*
import com.nilton.core.port.driver.employees.*
import com.nilton.core.query.companies.FindCompanyByIdQueryHandler
import com.nilton.core.query.companies.ListCompaniesQueryHandler
import com.nilton.core.query.employees.CalculateEmployeeSalaryQueryHandler
import com.nilton.core.query.employees.FindEmployeeByIdQueryHandler
import com.nilton.core.query.employees.ListEmployeesByCompanyIdQueryHandler
import com.nilton.core.query.employees.ListEmployeesQueryHandler
import com.nilton.infrastructure.adapter.driven.CompanyRepository
import com.nilton.infrastructure.adapter.driven.EmployeeRepository
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDependencyInjection() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}

val appModule = module {
    // Repositories
    single<ICompanyRepository> { CompanyRepository() }
    single<IEmployeeRepository> { EmployeeRepository() }

    // Company
    single<ICreateCompany> { CreateCompanyCommandHandler(get()) }
    single<IUpdateCompany> { UpdateCompanyCommandHandler(get()) }
    single<IDeleteCompany> { DeleteCompanyCommandHandler(get()) }
    single<IFindCompanyById> { FindCompanyByIdQueryHandler(get()) }
    single<IListCompanies> { ListCompaniesQueryHandler(get()) }

    // Employee
    single<ICreateEmployee> { CreateEmployeeCommandHandler(get()) }
    single<IUpdateEmployee> { UpdateEmployeeCommandHandler(get(), get()) }
    single<IDeleteEmployee> { DeleteEmployeeCommandHandler(get()) }
    single<IFindEmployeeById> { FindEmployeeByIdQueryHandler(get()) }
    single<IListEmployees> { ListEmployeesQueryHandler(get()) }
    single<IListEmployeesByCompanyId> { ListEmployeesByCompanyIdQueryHandler(get(), get()) }
    single<IChangeEmployeeType> { ChangeEmployeeTypeCommandHandler(get()) }
    single<ICalculateEmployeeSalary> { CalculateEmployeeSalaryQueryHandler(get()) }
}