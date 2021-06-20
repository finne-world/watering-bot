package com.it_finne.watering.application.json

import com.fasterxml.jackson.annotation.JsonProperty

data class AddResponse(
    @JsonProperty("status_code")
    val statusCode: Int,
    val timestamp: String,
    @JsonProperty("device_id")
    val deviceId: String,
    val setting: Setting
)