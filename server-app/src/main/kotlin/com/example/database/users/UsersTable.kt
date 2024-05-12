package ru.playzone.database.users

import com.example.database.users.UserDTO
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UsersTable : Table("users") {
    private val login = UsersTable.varchar("login", 25)
    private val password = UsersTable.varchar("password", 50)
    private val username = UsersTable.varchar("username", 30)
    private val email = UsersTable.varchar("email", 50)

    fun insert(userDTO: UserDTO) {
        transaction {
            UsersTable.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email ?: ""
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        val userModel = transaction { UsersTable.selectAll().where { UsersTable.login.eq(login) }.singleOrNull() }
        if (userModel != null) {
            return UserDTO(
                login = userModel[UsersTable.login],
                password = userModel[password],
                username = userModel[username],
                email = userModel[email]
            )
        }
        return null
    }
}