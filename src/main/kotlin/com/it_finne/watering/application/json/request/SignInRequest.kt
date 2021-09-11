package com.it_finne.watering.application.json.request

data class SignInRequest(
    val username: String,
    val password: String,
    val authorities: List<String>
)