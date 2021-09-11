package com.it_finne.watering.application.table

import com.it_finne.watering.application.entity.UsersEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object UsersTable : Table(name="users"){
    val username = varchar("username", 255)
    val userId = long("user_id")
    val discordUserId = long("discord_user_id")
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    fun toEntity(resultRow: ResultRow): UsersEntity {
        return UsersEntity(
            resultRow[UsersTable.username],
            resultRow[UsersTable.userId],
            resultRow[UsersTable.discordUserId],
            resultRow[UsersTable.createdAt],
            resultRow[UsersTable.updatedAt]
        )
    }
}