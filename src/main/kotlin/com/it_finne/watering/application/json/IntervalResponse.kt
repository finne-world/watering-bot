package com.it_finne.watering.application.json

data class IntervalResponse(
        val guildId: Long,
        val errorCode: Int?,
        val interval: Interval?
)