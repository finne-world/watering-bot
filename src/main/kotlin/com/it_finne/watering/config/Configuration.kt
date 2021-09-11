package com.it_finne.watering.config

import com.charleskorn.kaml.Yaml
import com.it_finne.watering.application.constant.ApplicationMode
import com.it_finne.watering.config.property.*
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val discordbot: DiscordBot,
    val database: Database,
    val api: Api,
    val aws: Aws,
    val messages: Messages
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
