package com.it_finne.watering.application.command

import com.it_finne.watering.application.http.HttpClient
import com.it_finne.watering.application.json.AddResponse
import com.it_finne.watering.application.json.ErrorResponse
import com.it_finne.watering.error.CommandWarningException
import com.it_finne.watering.config.Application
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper


class Add: Command() {
    init {
        this.name = "add"
    }

    override fun execute(event: CommandEvent) {
        val memberId = event.member.id
        val deviceName = if (event.args != "") {
            event.args.trim()
        } else {
            throw CommandWarningException(Application.configuration.message.deviceNotSpecified)
        }
        val parameter: Map<String, String> = mapOf(
                "device_name" to deviceName,
                "member_id" to memberId
        )

        val response = HttpClient().setParameter(parameter).setUrl(Application.configuration.api.add).setMethod("POST").execute()
        if (response.isSuccessful) {
            val addResponse: AddResponse = jacksonObjectMapper().readValue<AddResponse>(response.body()?.string(), AddResponse::class.java)
            event.replySuccess(Application.configuration.message.successfullyAddedDevice.format(deviceName, addResponse.deviceId))
        } else if (!response.isSuccessful) {
            val errorResponse: ErrorResponse = jacksonObjectMapper().readValue<ErrorResponse>(response.body()?.string(), ErrorResponse::class.java)
            for (error in errorResponse.errors) event.replyError("${error.code}:${error.message}")
        }
    }
}