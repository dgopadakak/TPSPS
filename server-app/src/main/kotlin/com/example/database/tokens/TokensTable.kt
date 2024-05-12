package ru.playzone.database.tokens

import com.example.database.tokens.TokenDTO
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TokensTable: Table("tokens") {
    private val id = integer("id").autoIncrement()
    private val login = varchar("login", 25)
    private val token = varchar("token", 75)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            TokensTable.insert {
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchToken(login: String): TokenDTO? {
        val tokenModel = transaction { TokensTable.selectAll().where { TokensTable.login.eq(login) }.singleOrNull() }
        if (tokenModel != null) {
            return TokenDTO(
                id = tokenModel[id],
                login = tokenModel[TokensTable.login],
                token = tokenModel[token]
            )
        }
        return null
    }
}