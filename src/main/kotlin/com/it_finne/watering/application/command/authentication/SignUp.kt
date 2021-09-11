package com.it_finne.watering.application.command.authentication

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.json.resopnse.ErrorResponse
import com.it_finne.watering.application.json.request.SignUpRequest
import com.it_finne.watering.application.json.resopnse.SignUpResponse
import com.it_finne.watering.application.repository.UsersRepository
import com.it_finne.watering.config.Application
import com.it_finne.watering.error.CommandWarningException
import com.it_finne.watering.application.http.HttpClient
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUp : Command(), KoinComponent {
    init {
        this.name = "sign_up"
    }

    override fun execute(event: CommandEvent) {
        val repository: UsersRepository = UsersRepository()
        val httpClient by inject<HttpClient>()
        val discordUserId: Long = event.author.id.toLong()
        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val arguments: List<String> = event.args.trim().split(Regex("\\p{javaWhitespace}"))
        if (arguments.size != 2) {
            throw CommandWarningException(Application.configuration.messages.tooManyOrTooFewArgument)
        }
        val username: String = arguments[0]
        val password: String = arguments[1]
        val sendDataJson: String = jacksonObjectMapper()
            .writeValueAsString(
                SignUpRequest(
                    username,
                    password,
                    listOf("SERVICE")
                )
            )

        httpClient.post(
            url = Application.configuration.api.signUp,
            mediaType = mediaType,
            sendData = sendDataJson,
        ).use {
            if (!it.isSuccessful) {
                val errorResponse = jacksonObjectMapper().readValue(it.body?.string(), ErrorResponse::class.java)
                for (error in errorResponse.errors) {
                    event.replyError("${error.code}:${error.message}")
                }
                return
            }

            val response = jacksonObjectMapper().readValue(it.body?.string(), SignUpResponse::class.java)
            repository.insert(response.username, response.id, discordUserId)

            event.replySuccess(Application.configuration.messages.userRegistrationSuccessful)
        }
    }
}