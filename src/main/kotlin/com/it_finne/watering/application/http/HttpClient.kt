package com.it_finne.watering.application.http

import okhttp3.MediaType
import okhttp3.Response

interface HttpClient {
    fun delete(url: String, sendData: String, mediaType: MediaType, userId: Long? = null): Response

    fun get(url:String, sendData: String? = null, mediaType: MediaType? = null, userId: Long? = null): Response

    fun post(url: String, sendData: String, mediaType: MediaType, userId: Long? = null): Response

    fun put(url: String, sendData: String, mediaType: MediaType, userId: Long? = null): Response
}