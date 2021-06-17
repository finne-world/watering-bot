package com.it_finne.watering.config.property

import kotlinx.serialization.Serializable

@Serializable
data class Message (
     val successfullyAddedDevice: String,
     val deviceNotSpecified: String
)