package com.it_finne.watering.application.json.request

data class SignUpRequest(
    val username: String,
    val password: String,
    val authorities: List<String>
)
