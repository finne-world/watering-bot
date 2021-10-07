package com.it_finne.watering.application.repository

import arrow.core.Option
import arrow.core.firstOrNone
import com.it_finne.watering.application.entity.TokenEntity
import com.it_finne.watering.application.table.TokenTable
import com.it_finne.watering.error.UpdateFailedException
import com.it_finne.watering.lib.extention.isPositive
import com.it_finne.watering.lib.extention.runIf
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.UUID

class TokenRepository {
    fun insert(userId: Long,accessToken: String, refreshTokens: UUID, expiresAt: Int, ) {
        transaction {
            TokenTable.insert {
                it[this.userId] = userId
                it[this.accessToken] = accessToken
                it[this.refreshTokens] = refreshTokens
                it[this.expiresAt] = expiresAt
            }
        }
    }


    fun updateRefreshToken(userId: Long, newToken: UUID) {
        transaction {
            TokenTable.update({ TokenTable.userId eq userId }) {
                it[this.refreshTokens] = newToken
                it[this.updatedAt] = LocalDateTime.now()
            }.isPositive().runIf(false) {
                throw UpdateFailedException("Failed to update resource in table [refresh_tokens].")
            }
        }
    }

    fun updateAccessToken(userId: Long, newToken: String) {
        transaction {
            TokenTable.update({ TokenTable.userId eq userId }) {
                it[this.accessToken] = newToken
                it[this.updatedAt] = LocalDateTime.now()
            }.isPositive().runIf(false) {
                throw UpdateFailedException("Failed to update resource in table [access_tokens].")
            }
        }
    }

    fun updateExpiresAt(userId: Long, newExpiresAt: Int,) {
        transaction {
            TokenTable.update({ TokenTable.userId eq userId }) {
                it[this.expiresAt] = expiresAt
                it[this.updatedAt] = LocalDateTime.now()
            }.isPositive().runIf(false) {
                throw UpdateFailedException("Failed to update resource in table [expires_at].")
            }
        }
    }

    fun getByUserId(userId: Long): Option<TokenEntity> {
        val result = transaction {
            TokenTable.select { TokenTable.userId eq userId }.map(TokenTable::toEntity).firstOrNone()
        }
        return result
    }

    fun isExistByUerId(userId: Long): Boolean {
        return transaction { TokenTable.select { TokenTable.userId eq userId }.count() > 0 }
    }
}

/*
        return TokenTable.update({ TokenTable.userId eq userId }) {
            it[this.accessToken] = newToken
            it[this.updatedAt] = LocalDateTime.now()
        }.isPositive().runIf(false) {
            throw UpdateFailedException("Failed to update resource in table [access_tokens].")
        }
 */