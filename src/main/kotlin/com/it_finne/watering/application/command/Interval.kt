package com.it_finne.watering.application.command

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.http.HttpClient
import com.it_finne.watering.application.json.IntervalResponse
import com.it_finne.watering.config.Application
import com.it_finne.watering.error.CommandErrorException
import com.it_finne.watering.error.CommandWarningException
import com.it_finne.watering.error.ApiErrorManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import mu.KLogger
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Request
import okhttp3.FormBody

class Interval(
        private val logger: KLogger = KotlinLogging.logger {}
): Command() {
    init {
        this.name = "interval"
    }

    override fun execute(event: CommandEvent) {
        val argument: String = event.args.trim()
        val interval: Long? = argument.toLongOrNull() ?: throw CommandWarningException("Please enter interval time as an integer.")
        val parameter: Map<String, String> = mapOf("guild_id" to event.guild.id, "interval" to interval.toString())

        val response = HttpClient(this.name).setParameter(parameter).execute()
        val responseBody: IntervalResponse = jacksonObjectMapper().readValue<IntervalResponse>(response.body()?.string(), IntervalResponse::class.java)

        if (!response.isSuccessful) {
            val errorResponse: String = ApiErrorManager().getErrorResponseByErrorCode(responseBody.errorCode.toString())
            throw CommandErrorException(errorResponse)
        } else {
            event.replySuccess("水やりの間隔を${responseBody.interval?.old}秒から${responseBody.interval?.new}秒へ変更しました！")
        }
    }
}