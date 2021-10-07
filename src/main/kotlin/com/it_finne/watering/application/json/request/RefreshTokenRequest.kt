package com.it_finne.watering.application.json.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RefreshTokenRequest (
    @JsonProperty("refresh_token")
    val refreshToken: UUID
)