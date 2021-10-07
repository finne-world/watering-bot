package com.it_finne.watering.application.entity

import java.time.LocalDateTime

data class UsersEntity (
    val username: String,
    val userId: Long,
    val discordUserId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)