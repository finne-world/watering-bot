package com.it_finne.watering.application.http

import arrow.core.getOrElse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.json.resopnse.RefreshTokenResponse
import com.it_finne.watering.application.repository.TokenRepository
import com.it_finne.watering.error.ResourceNotFoundException
import java.util.UUID
import okhttp3.Response
import org.jetbrains.exposed.sql.transactions.transaction

internal class TokensManager {
    private val tokenRepository: TokenRepository = TokenRepository()

    fun getRefreshToken(userId: Long): UUID {
        val refreshToken = tokenRepository.getByUserId(userId).getOrElse {
                throw ResourceNotFoundException("Cannot find the column corresponding to the user Id")
            }.refreshToken

        return refreshToken
    }

    fun getAccessToken(userId: Long): String {
        val accessToken = tokenRepository.getByUserId(userId).getOrElse {
                throw ResourceNotFoundException("Cannot find the column corresponding to the user Id")
            }.accessToken

        return accessToken
    }

    fun updateTokens(userId: Long, accessToken: String, refreshToken: UUID, expiresIn: Int) {
        tokenRepository.updateAccessToken(userId, accessToken)
        tokenRepository.updateRefreshToken(userId, refreshToken)
        tokenRepository.updateExpiresAt(userId, expiresIn)
    }
}