package com.it_finne.watering.application.json

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse (
        @JsonProperty("status_code")
        val statusCode: String,
        val timestamp: String,
        val errors: List<Error>
)