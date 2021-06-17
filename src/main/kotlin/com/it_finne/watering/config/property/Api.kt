package com.it_finne.watering.config.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Api(
    @SerialName("auto_watering")
    val autoWatering: String,
    val interval: String,
    val add: String
)
