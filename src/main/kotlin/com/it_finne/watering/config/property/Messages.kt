package com.it_finne.watering.config.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Messages(
    @SerialName("device_added_successfully")
    val deviceAddedSuccessfully: String,
    @SerialName("device_not_specified")
    val deviceNotSpecified: String,
    @SerialName("device_contains_space")
    val deviceContainsSpace: String,
    @SerialName("too_many_or_too_few_argument")
    val tooManyOrTooFewArgument: String,
    @SerialName("user_registration_successful")
    val userRegistrationSuccessful: String,
    @SerialName("login_successful")
    val loginSuccessful: String
)
