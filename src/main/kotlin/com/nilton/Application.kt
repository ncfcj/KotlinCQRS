package com.nilton

import com.nilton.app.configureDependencyInjection
import com.nilton.app.configureSerialization
import com.nilton.app.configureStatusPages
import com.nilton.infrastructure.adapter.driver.http.companiesController
import com.nilton.infrastructure.adapter.driver.http.employeesController
import com.nilton.infrastructure.database.initDatabase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    initDatabase()
    configureSerialization()
    configureStatusPages()
    configureDependencyInjection()

    companiesController()
    employeesController()
}
