package com.nilton.infrastructure.adapter.driver.http

import com.nilton.core.port.driver.companies.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.companiesController(){
    val createCompanyCommandHandler : ICreateCompany by inject<ICreateCompany>()
    val updateCompanyCommandHandler : IUpdateCompany by inject<IUpdateCompany>()
    val deleteCompanyCommandHandler : IDeleteCompany by inject<IDeleteCompany>()
    val findCompanyByIdQueryHandler : IFindCompanyById by inject<IFindCompanyById>()
    val listCompaniesQueryHandler : IListCompanies by inject<IListCompanies>()

    routing {
        post("/companies"){
            val model = call.receive<CreateCompanyViewModel>()
            val command = CreateCompanyCommand(model.name)
            val response = createCompanyCommandHandler(command)

            when (response) {
                is CreateCompanyDto.Success -> call.respond(HttpStatusCode.Created, response)
                is CreateCompanyDto.Failure -> call.respond(response.error.code, response)
            }
        }

        put("/companies/{id}"){
            val companyId = UUID.fromString(call.parameters["id"])
            val model = call.receive<UpdateCompanyViewModel>()
            val command = UpdateCompanyCommand(model.name, companyId)
            val response = updateCompanyCommandHandler(command)

            when (response) {
                is UpdateCompanyDto.Success -> call.respond(HttpStatusCode.OK, response)
                is UpdateCompanyDto.Failure -> call.respond(response.error.code, response)
            }
        }

        delete("/companies/{id}"){
            val companyId = UUID.fromString(call.parameters["id"])
            val command = DeleteCompanyCommand(companyId)
            val response = deleteCompanyCommandHandler(command)

            when (response) {
                is DeleteCompanyDto.Success -> call.respond(HttpStatusCode.OK, response)
                is DeleteCompanyDto.Failure -> call.respond(response.error.code, response)
            }
        }

        get("/companies/{id}"){
            val companyId = UUID.fromString(call.parameters["id"])
            val query = FindCompanyByIdQuery(companyId)
            val response = findCompanyByIdQueryHandler(query)

            when (response) {
                is FindCompanyByIdDto.Success -> call.respond(HttpStatusCode.OK, response)
                is FindCompanyByIdDto.Failure -> call.respond(response.error.code, response)
            }
        }

        get("/companies"){
            val response = listCompaniesQueryHandler()

            when (response) {
                is ListCompaniesDto.Success -> call.respond(HttpStatusCode.OK, response)
                is ListCompaniesDto.Failure -> call.respond(response.error.code, response)
            }
        }
    }
}

data class CreateCompanyViewModel(val name: String)

data class UpdateCompanyViewModel(val name: String)