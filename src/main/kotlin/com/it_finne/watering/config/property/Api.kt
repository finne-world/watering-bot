package com.it_finne.watering.config.property

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Api(
    @SerialName("auto_watering")
<<<<<<< HEAD
    val autoWatering: String,
    val add: String,
    @SerialName("sign_up")
    val signUp: String,
    @SerialName("sign_in")
    val signIn: String
=======
    val autoWatering: String
>>>>>>> 17ebb819d83a5ac13bcd08bcc6916eff5fc462bb
)
