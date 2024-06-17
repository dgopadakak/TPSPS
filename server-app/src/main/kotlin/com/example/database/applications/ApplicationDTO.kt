package com.example.database.applications

data class ApplicationDTO(
    val id: Int,
    val name: String,
    val description: String,
    val employeeId: Int,
    val executorId: Int
)