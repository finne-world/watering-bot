package com.it_finne.watering.application.json.resopnse

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpResponse (
    @JsonProperty("status_code")
    val statusCode: Int,
    val timestamp: String,
    val id: Long,
    val username: String,
    val authorities: List<String>
)