package com.it_finne.watering.config

import com.charleskorn.kaml.Yaml
import com.it_finne.watering.application.constant.ApplicationMode
<<<<<<< HEAD
import com.it_finne.watering.config.property.*
=======
import com.it_finne.watering.config.property.Api
import com.it_finne.watering.config.property.Database
import com.it_finne.watering.config.property.DiscordBot
>>>>>>> 17ebb819d83a5ac13bcd08bcc6916eff5fc462bb
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val discordbot: DiscordBot,
    val database: Database,
<<<<<<< HEAD
    val api: Api,
    val aws: Aws,
    val messages: Messages
=======
    val api: Api
>>>>>>> 17ebb819d83a5ac13bcd08bcc6916eff5fc462bb
) {
    companion object {
        fun load(applicationMode: ApplicationMode): Configuration {
            val filename = "/application-${applicationMode.value}.yaml"
            return Yaml.default.decodeFromString(
                serializer(),
                Configuration::class.java.getResource(filename).readText()
            )
        }
    }
}
