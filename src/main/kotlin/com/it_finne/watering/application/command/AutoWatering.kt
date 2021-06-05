package com.it_finne.watering.application.command

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.json.AutoWateringResponse
import com.it_finne.watering.config.Application
import com.it_finne.watering.error.CommandErrorException
import com.it_finne.watering.error.CommandWarningException
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import mu.KLogger
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.RequestBody
import okhttp3.Request
import okhttp3.FormBody

class AutoWatering(
    private val logger: KLogger = KotlinLogging.logger {}
): Command() {
    init {
        this.name = "auto_watering"
    }

    override fun execute(event: CommandEvent) {
        val argument: String = event.args.trim()

        if (!listOf("on", "off").contains(argument)) {
            throw CommandWarningException("Please enter `on` or `off`.")
        }

        val requestBody: RequestBody = FormBody.Builder().add("on", argument).build()

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val request: Request = Request.Builder().url(Application.configuration.api.autoWatering).post(requestBody).build()

        val response: Response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            throw CommandErrorException("Error")
        }

        val responseBody: AutoWateringResponse = jacksonObjectMapper().readValue(response.body()?.string(), AutoWateringResponse::class.java)
        if (responseBody.result == "failure") {
            event.replyError(responseBody.errorMessage)
        }
    }
}
