package com.nilton.infrastructure.adapter.driver.http

import com.nilton.core.domain.employees.EmployeeType
import com.nilton.core.port.driver.employees.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.employeesController() {
    val createEmployeeCommandHandler by inject<ICreateEmployee>()
    val updateEmployeeCommandHandler by inject<IUpdateEmployee>()
    val deleteEmployeeCommandHandler by inject<IDeleteEmployee>()
    val findEmployeeByIdQueryHandler by inject<IFindEmployeeById>()
    val listEmployeesQueryHandler by inject<IListEmployees>()
    val listEmployeesByCompanyIdQueryHandler by inject<IListEmployeesByCompanyId>()
    val changeEmployeeTypeCommandHandler by inject<IChangeEmployeeType>()
    val calculateEmployeeSalaryQueryHandler by inject<ICalculateEmployeeSalary>()

    routing {
        post("/employees") {
            val model = call.receive<CreateEmployeeViewModel>()
            val command = CreateEmployeeCommand(
                model.name,
                model.salaryByHour,
                model.expectedWorkingHours,
                model.companyId,
                model.type
            )
            val response = createEmployeeCommandHandler(command)

            when (response) {
                is CreateEmployeeDto.Success -> call.respond(HttpStatusCode.Created, response)
                is CreateEmployeeDto.Failure -> call.respond(HttpStatusCode.BadRequest, response)
                is CreateEmployeeDto.ValidationFailure -> call.respond(HttpStatusCode.BadRequest, response)
            }
        }

        put("/employees/{id}") {
            val employeeId = UUID.fromString(call.parameters["id"])
            val model = call.receive<UpdateEmployeeViewModel>()
            val command = UpdateEmployeeCommand(
                employeeId,
                model.name,
                model.salaryByHour,
                model.expectedWorkingHours
            )

            val response = updateEmployeeCommandHandler(command)

            when (response) {
                is UpdateEmployeeDto.Success -> call.respond(HttpStatusCode.OK, response)
                is UpdateEmployeeDto.Failure -> call.respond(response.error.code, response)
                is UpdateEmployeeDto.ValidationFailure -> call.respond(HttpStatusCode.BadRequest, response)
            }
        }

        delete("/employees/{id}") {
            val employeeId = UUID.fromString(call.parameters["id"])
            val command = DeleteEmployeeCommand(employeeId)
            val response = deleteEmployeeCommandHandler.invoke(command)

            when (response) {
                is DeleteEmployeeDto.Success -> call.respond(HttpStatusCode.NoContent, response)
                is DeleteEmployeeDto.Failure -> call.respond(response.error.code, response)
            }
        }

        patch("/employees/{id}/ChangeType") {
            val employeeId = UUID.fromString(call.parameters["id"])
            val model = call.receive<ChangeEmployeeTypeViewModel>()
            val command = ChangeEmployeeTypeCommand(employeeId, model.type)
            val response = changeEmployeeTypeCommandHandler(command)

            when (response) {
                is ChangeEmployeeTypeDto.Success -> call.respond(HttpStatusCode.OK, response)
                is ChangeEmployeeTypeDto.Failure -> call.respond(response.error.code, response)
            }
        }

        get("/employees/{id}") {
            val employeeId = UUID.fromString(call.parameters["id"])
            val query = FindEmployeeByIdQuery(employeeId)
            val response = findEmployeeByIdQueryHandler(query)
            when (response) {
                is FindEmployeeByIdDto.Success -> call.respond(HttpStatusCode.OK, response)
                is FindEmployeeByIdDto.Failure -> call.respond(response.error.code, response)
            }
        }

        get("/employees"){
            val response = listEmployeesQueryHandler()
            when (response) {
                is ListEmployeesDto.Success -> call.respond(HttpStatusCode.OK,response)
            }
        }

        get("/employees/ByCompany/{companyId}") {
            val companyId = UUID.fromString(call.parameters["companyId"])
            val query = ListEmployeeByCompanyIdQuery(companyId)
            val response = listEmployeesByCompanyIdQueryHandler(query)

            when (response) {
                is ListEmployeeByCompanyIdDto.Success -> call.respond(HttpStatusCode.OK,response)
                is ListEmployeeByCompanyIdDto.Failure -> call.respond(response.error.code, response)
            }
        }

        get("/employees/{id}/CalculateExpectedSalary"){
            val employeeId = UUID.fromString(call.parameters["id"])
            val query = CalculateEmployeeSalaryQuery(employeeId)
            val response = calculateEmployeeSalaryQueryHandler(query)
            when (response) {
                is CalculateEmployeeSalaryDto.Success -> call.respond(HttpStatusCode.OK,response)
                is CalculateEmployeeSalaryDto.Failure -> call.respond(response.error.code, response)
            }
        }
    }
}

data class CreateEmployeeViewModel(
    val name : String?,
    val salaryByHour : Double?,
    val expectedWorkingHours : Int?,
    val companyId : UUID?,
    val type : EmployeeType?
)

data class UpdateEmployeeViewModel(
    val name : String?,
    val salaryByHour : Double?,
    val expectedWorkingHours : Int?
)

data class ChangeEmployeeTypeViewModel(val type : EmployeeType)
