package com.it_finne.watering.application.http

import okhttp3.*

class HttpClient() {
    private val formBuilder: FormBody.Builder = FormBody.Builder()
    private val requestBuilder: Request.Builder = Request.Builder()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    private var method: String = ""

    fun setParameter(parameter: Map<String,String>): HttpClient {
        parameter.forEach{ formBuilder.add(it.key, it.value) }
        return this
    }

    fun setUrl(url: String): HttpClient {
        requestBuilder.url(url)
        return this
    }

    fun setMethod(method: String): HttpClient {
        if (!listOf("POST", "GET").contains(method)) {
            throw IllegalArgumentException("The http method name is incorrect.")
        }
        this.method = method
        return this
    }

    fun execute(): Response {
        when (method) {
            "POST" -> requestBuilder.post(formBuilder.build())
            // "GET" -> Not implemented yet.
        }
        val request = requestBuilder.build()
        return okHttpClient.newCall(request).execute()
    }
}