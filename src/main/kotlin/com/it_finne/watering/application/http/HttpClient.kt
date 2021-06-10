package com.it_finne.watering.application.http

import com.it_finne.watering.config.Application
import com.it_finne.watering.error.CommandErrorException
import okhttp3.*

class HttpClient(commandName: String) {
    private val formBuilder: FormBody.Builder = FormBody.Builder()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    private val commandUrl: String = when(commandName){
        "interval" -> Application.configuration.api.interval
        "auto_watering" -> Application.configuration.api.autoWatering
        "init" -> Application.configuration.api.init
        else -> throw IllegalArgumentException("Could not find the url for the specified command.:$commandName")
    }

    fun setParameter(parameter: Map<String,String>): HttpClient {
        parameter.forEach{ key: String, value: String -> formBuilder.add(key, value) }
        return this
    }

    fun execute(): Response {
        val requestBody = formBuilder.build()
        val request: Request = Request.Builder().url(commandUrl).post(requestBody).build()
        return okHttpClient.newCall(request).execute()
    }
}