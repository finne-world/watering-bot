package com.it_finne.watering.application.json.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DeviceAddRequest(
    @JsonProperty("device_name")
    val deviceName: String,
    @JsonProperty("member_id")
    val memberId: Long,
    val authorities: List<String>
)
