package com.it_finne.watering.application.json

import com.fasterxml.jackson.annotation.JsonProperty

data class Setting (
        @JsonProperty("auto_watering")
        val autoWatering: Boolean,
        val interval: Int
)