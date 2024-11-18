package com.nilton.infrastructure.database

import com.nilton.core.domain.employees.EmployeeType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun initDatabase(){
    val dbName = System.getenv("DB_NAME")
    val dbUser = System.getenv("DB_USER")
    val dbPassword = System.getenv("DB_PASSWORD")


    Database.connect(
        url = "jdbc:postgresql://postgres:5432/$dbName",
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(Companies, Employees)
    }
}

object Employees : Table("employees") {
    val id = uuid("id").default(UUID.randomUUID())
    val name = varchar("name", 255)
    val salaryByHour = double("salaryByHour")
    val expectedWorkingHours = integer("expectedWorkingHours")
    val companyId = uuid("companyId") references Companies.id
    val type = enumeration<EmployeeType>("type")

    override val primaryKey = PrimaryKey(id)
}

object Companies : Table("companies") {
    val id = uuid("id").default(UUID.randomUUID())
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)
}