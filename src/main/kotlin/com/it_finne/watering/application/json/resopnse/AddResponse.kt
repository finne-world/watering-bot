package com.it_finne.watering.application.json.resopnse

import com.fasterxml.jackson.annotation.JsonProperty

data class AddResponse(
    @JsonProperty("status_code")
    val statusCode: Int,
    val timestamp: String,
    @JsonProperty("device_id")
    val deviceId: String,
    @JsonProperty("watering_setting")
    val wateringSetting: WateringSetting,
    @JsonProperty("auto_watering_setting")
    val autoWateringSetting: AutoWateringSetting
)

data class AutoWateringSetting(
    val enabled: Boolean,
    val interval: Int
)

data class WateringSetting(
    @JsonProperty("water_amount")
    val waterAmount: Int
)