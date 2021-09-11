package com.it_finne.watering.application.entity

import java.time.LocalDateTime
import java.util.UUID

data class TokenEntity (
    val userId: Long,
    val accessToken: String,
    val refreshToken: UUID,
    val expiresAt: Int,
    val createdAt: LocalDateTime,
    val updateAt: LocalDateTime
)