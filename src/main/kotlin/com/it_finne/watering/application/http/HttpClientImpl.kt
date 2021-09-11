package com.it_finne.watering.application.http

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.it_finne.watering.application.json.resopnse.ErrorResponse
import com.it_finne.watering.application.json.request.RefreshTokenRequest
import com.it_finne.watering.application.json.resopnse.RefreshTokenResponse
import com.it_finne.watering.error.FailedToTokenUpdateException
import com.it_finne.watering.error.MultipleErrorsInResponseException
import com.it_finne.watering.error.RefreshTokenExpiresException
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class HttpClientImpl : HttpClient {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val tokensManager: TokensManager = TokensManager()

    private fun getNewTokens(userId: Long) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val url = "http://localhost:8080/api/authentication/refresh_token"
        val refreshToken = RefreshTokenRequest(tokensManager.getRefreshToken(userId))
        val sendData = jacksonObjectMapper().writeValueAsString(refreshToken)

        post(url, sendData, mediaType).use{
            if (!it.isSuccessful) {
                val body = jacksonObjectMapper().readValue(it.body?.string(), ErrorResponse::class.java)

                if (body.errors.size >= 2) {
                    throw MultipleErrorsInResponseException(body.errors.toString())
                }

                val error = body.errors[0]
                if (error.code == 1011) {
                    throw RefreshTokenExpiresException(error.message)
                }
                throw FailedToTokenUpdateException(error.message)
            }

            val body = jacksonObjectMapper().readValue(it.body?.string(), RefreshTokenResponse::class.java)
            tokensManager.updateTokens(userId, body.accessToken, body.refreshToken, body.expiresIn)
        }
    }

    private fun requestBuild(
        url: String,
        method: String,
        sendData: String? = null,
        mediaType: MediaType? = null,
        accessToken: String? = null
    ): Request {
        val request = Request.Builder()
        val sendData = sendData?.toRequestBody(mediaType)

        if (accessToken != null) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }
        return request.method(method, sendData).url(url).build()
    }

    private fun execute(
        method: String,
        url: String,
        sendData: String?,
        mediaType: MediaType?,
        userId: Long? = null,
        limitOfRetry: Int = 3
    ): Response {
        var response: Response

        var retryCount: Int = 0
        do {
            var accessToken = userId?.let { tokensManager.getAccessToken(it) }
            var request = requestBuild(url, method, sendData, mediaType, accessToken)
            response = client.newCall(request).execute()
            if (response.isSuccessful) {
                break
            } else if (response.code == 401) {
                userId?.let { getNewTokens(it) }
            }
            retryCount++
        } while (retryCount <= limitOfRetry)

        return response
    }

    override fun delete(url: String, sendData: String, mediaType: MediaType, userId: Long?): Response {
        return execute("DELETE", url, sendData, mediaType, userId)
    }

    override fun get(url:String, sendData: String?, mediaType: MediaType?, userId: Long?): Response {
        TODO("Not implemented yet.")
    }

    override fun post(url: String, sendData: String, mediaType: MediaType, userId: Long?): Response {
        return execute("POST", url, sendData, mediaType, userId)
    }

    override fun put(url: String, sendData: String, mediaType: MediaType, userId: Long?): Response {
        TODO("Not implemented yet.")
    }
}