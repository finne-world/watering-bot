package com.it_finne.watering.application.http

import okhttp3.Response
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpClient {
    private val formBuilder: FormBody.Builder = FormBody.Builder()
    private val requestBuilder: Request.Builder = Request.Builder()
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    private fun request(
            method: String,
            url: String,
            parameter: Array<out Pair<String, String>>? = null
    ): Response {
        requestBuilder.url(url)
        parameter?.forEach{ formBuilder.add(it.first, it.second) }
        when (method) {
            "DELETE" -> requestBuilder.delete()
            "GET" -> requestBuilder.get()
            "POST" -> requestBuilder.post(formBuilder.build())
            "PUT" -> requestBuilder.put(formBuilder.build())
        }
        val request = requestBuilder.build()
        return okHttpClient.newCall(request).execute()
    }

    fun delete(url: String) = this.request("DELETE", url)

    fun get(url: String) = this.request("GET", url)

    fun post(url: String, vararg parameter: Pair<String, String>) = this.request("POST", url, parameter)

    fun put(url: String, vararg parameter: Pair<String, String>) = this.request("PUT", url, parameter)
/*
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
*/
}