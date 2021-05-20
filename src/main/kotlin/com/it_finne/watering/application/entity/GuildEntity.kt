package com.it_finne.watering.application.entity

import java.time.LocalDateTime

data class GuildEntity(
    val id: Long,
    val guildId: Long,
    val name: String,
    val prefix: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
