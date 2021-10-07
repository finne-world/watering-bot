package com.it_finne.watering.application.table

import com.it_finne.watering.application.entity.TokenEntity
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object TokenTable : Table(name="tokens") {
    val userId = reference("user_id", UsersTable.userId, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val accessToken = text("access_token")
    val refreshTokens = uuid("refresh_token")
    val expiresAt = integer("expires_at")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): TokenEntity {
        return TokenEntity(
            resultRow[userId],
            resultRow[accessToken],
            resultRow[refreshTokens],
            resultRow[expiresAt],
            resultRow[createdAt],
            resultRow[updatedAt]
        )
    }
}