package com.it_finne.watering.config

import com.charleskorn.kaml.Yaml
import com.it_finne.watering.application.constant.ApplicationMode
import com.it_finne.watering.config.property.Api
import com.it_finne.watering.config.property.Database
import com.it_finne.watering.config.property.DiscordBot
import com.it_finne.watering.config.property.Message
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val discordbot: DiscordBot,
    val database: Database,
    val api: Api,
    val message: Message
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
