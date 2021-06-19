package com.it_finne.watering.application.command.watering.device

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
        val memberId: Pair<String, String> = "member_id" to event.member.id
        val deviceName: Pair<String, String> = "device_name" to event.args.trim()
        if (deviceName.second.isEmpty()) {
            throw CommandWarningException(Application.configuration.message.deviceNotSpecified)
        } else if (deviceName.second.contains(Regex("\\p{javaWhitespace}"))) {
            throw CommandWarningException(Application.configuration.message.deviceNameContainsSpace)
        }

        HttpClient().post(Application.configuration.api.add, memberId , deviceName).use {
            if (!it.isSuccessful) {
                val errorResponse = jacksonObjectMapper().readValue(it.body?.string(), ErrorResponse::class.java)
                for (error in errorResponse.errors) {
                    event.replyError("${error.code}:${error.message}")
                }
                return
            }
            val addResponse = jacksonObjectMapper().readValue(it.body?.string(), AddResponse::class.java)
            event.replySuccess(Application.configuration.message.successfullyAddedDevice.format(deviceName, addResponse.deviceId))
        }
    }
}