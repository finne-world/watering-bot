package com.it_finne.watering.application.json.resopnse

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse (
    @JsonProperty("status_code")
    val statusCode: String,
    val timestamp: String,
    val errors: List<Error>,
    val path: String
)

data class Error(
    val code: Int,
    val message: String
)