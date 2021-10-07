package com.it_finne.watering.application.command.device

import arrow.core.getOrElse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.json.resopnse.ErrorResponse
import com.it_finne.watering.application.json.request.DeviceAddRequest
import com.it_finne.watering.application.repository.UsersRepository
import com.it_finne.watering.config.Application
import com.it_finne.watering.error.CommandWarningException
import com.it_finne.watering.error.ResourceNotFoundException
import com.it_finne.watering.application.http.HttpClient
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Add : Command(), KoinComponent{
    init {
        this.name = "add"
    }

    override fun execute(event: CommandEvent) {
        val httpClient by inject<HttpClient>()
        val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
        val repository = UsersRepository()
        val userId: Long = repository.getByDiscordUserId(event.author.idLong).getOrElse {
            throw ResourceNotFoundException("Cannot find the column corresponding to the user Id")
        }.userId
        val deviceName: String = event.args.trim()
        if (deviceName.isEmpty()) {
            throw CommandWarningException(Application.configuration.messages.deviceNotSpecified)
        } else if (deviceName.contains(Regex("\\p{javaWhitespace}"))) {
            throw CommandWarningException(Application.configuration.messages.deviceContainsSpace)
        }
        val sendData: String = jacksonObjectMapper()
            .writeValueAsString(
                DeviceAddRequest(
                    deviceName,
                    userId,
                    listOf("SERVICE")
                )
            )

        httpClient.post(
            url = Application.configuration.api.add,
            sendData = sendData,
            mediaType = mediaType,
            userId = userId
        ).use{
            if (!it.isSuccessful) {
                val errorResponse = jacksonObjectMapper().readValue(it.body?.string(), ErrorResponse::class.java)
                for (error in errorResponse.errors) {
                    event.replyError("${error.code}:${error.message}")
                }
                return
            }

            event.replySuccess(Application.configuration.messages.deviceAddedSuccessfully)
        }
    }
}