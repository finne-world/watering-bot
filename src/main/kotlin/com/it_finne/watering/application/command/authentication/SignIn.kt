package com.it_finne.watering.application.command.authentication

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.json.resopnse.ErrorResponse
import com.it_finne.watering.application.json.request.SignInRequest
import com.it_finne.watering.application.json.resopnse.SignInResponse
import com.it_finne.watering.application.repository.TokenRepository
import com.it_finne.watering.config.Application
import com.it_finne.watering.error.CommandWarningException
import com.it_finne.watering.application.http.HttpClient
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignIn : Command(), KoinComponent {
    init {
        this.name = "sign_in"
    }

    override fun execute(event: CommandEvent) {
        val repository: TokenRepository = TokenRepository()
        val httpClient by inject<HttpClient>()
        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val arguments: List<String> = event.args.trim().split(Regex("\\p{javaWhitespace}"))
        if (arguments.size != 2) {
            throw CommandWarningException(Application.configuration.messages.tooManyOrTooFewArgument)
        }
        val username: String = arguments[0]
        val password: String = arguments[1]
        val sendDataJson: String = jacksonObjectMapper()
            .writeValueAsString(
                SignInRequest(
                    username,
                    password,
                    listOf("SERVICE")
                )
            )

        httpClient.post(
            url = Application.configuration.api.signIn,
            sendData = sendDataJson,
            mediaType = mediaType
        ).use {
            if (!it.isSuccessful) {
                val errorResponse = jacksonObjectMapper().readValue(it.body?.string(), ErrorResponse::class.java)
                for (error in errorResponse.errors) {
                    event.replyError("${error.code}:${error.message}")
                }
                return
            }

            val signInResponse = jacksonObjectMapper().readValue(it.body?.string(), SignInResponse::class.java)

            val userId = signInResponse.id
            val aToken = signInResponse.accessToken
            val rToken = signInResponse.refreshToken
            val expiresIn = signInResponse.expiresIn

            if(!repository.isExistByUerId(userId)) {
                repository.insert(userId, aToken, rToken, expiresIn)
            } else {
                repository.updateAccessToken(userId, aToken)
                repository.updateRefreshToken(userId, rToken)
                repository.updateExpiresAt(userId, expiresIn)
            }
            event.replySuccess(Application.configuration.messages.loginSuccessful)
        }
    }
}