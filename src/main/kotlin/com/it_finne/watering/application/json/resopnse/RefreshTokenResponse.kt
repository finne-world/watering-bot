package com.it_finne.watering.application.json.resopnse

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RefreshTokenResponse(
    @JsonProperty("status_code")
    val statusCode: Int,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: UUID,
    @JsonProperty("token_type")
    val tokenType: String
)
