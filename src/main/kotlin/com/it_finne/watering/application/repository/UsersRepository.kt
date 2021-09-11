package com.it_finne.watering.application.repository

import arrow.core.Option
import arrow.core.firstOrNone
import com.it_finne.watering.application.entity.UsersEntity
import com.it_finne.watering.application.table.UsersTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UsersRepository {
    fun insert(username: String, userId: Long, discordUserId: Long) {
        transaction {
            UsersTable.insert {
                it[this.username] = username
                it[this.userId] = userId
                it[this.discordUserId] = discordUserId
            }
        }
    }


    fun getByDiscordUserId(userId: Long): Option<UsersEntity> {
        val result = transaction {
            UsersTable.select { UsersTable.discordUserId eq userId }.map(UsersTable::toEntity).firstOrNone()
        }

        return result
    }
}