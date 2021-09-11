package com.it_finne.watering.application.json.resopnse

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class SignInResponse (
    val id: Long,
    val username: String,
    val authorities: List<String>,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    val refreshToken: UUID,
    @JsonProperty("token_type")
    val tokenType: String
)
