package com.example.database.applications

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object ApplicationTable : Table("applications") {
    val id = ApplicationTable.integer("id").autoIncrement()
    val name = ApplicationTable.varchar("name", 50)
    val description = ApplicationTable.varchar("description", 100)
    val employeeId = ApplicationTable.integer("employeeId")
    val executorId = ApplicationTable.integer("executorId")

    fun fetchApplication(id: Int): ApplicationDTO? {
        val applicationModel = transaction { ApplicationTable.selectAll().where { ApplicationTable.id.eq(id) }.singleOrNull() }
        if (applicationModel != null) {
            return ApplicationDTO(
                id = applicationModel[ApplicationTable.id],
                name = applicationModel[name],
                description = applicationModel[description],
                employeeId = applicationModel[employeeId],
                executorId = applicationModel[executorId]
            )
        }
        return null
    }

    fun insert(applicationDTO: ApplicationDTO) {
        transaction {
            ApplicationTable.insert {
                it[id] = applicationDTO.id
                it[name] = applicationDTO.name
                it[description] = applicationDTO.description
                it[employeeId] = applicationDTO.employeeId
                it[executorId] = applicationDTO.executorId
            }
        }
    }
}