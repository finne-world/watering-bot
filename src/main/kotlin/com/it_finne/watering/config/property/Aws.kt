package com.it_finne.watering.config.property

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aws(
    @Contextual
    @SerialName("parameter-store")
    val parameterStore: ParameterStoreProperties,
)

@Serializable
data class ParameterStoreProperties(
    @Contextual
    val jwt: JwtProperties
)

@Serializable
data class JwtProperties(
    @SerialName("public-key")
    val publicKey: String,
    @SerialName("private-key")
    val privateKey: String
)
