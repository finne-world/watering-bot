package com.it_finne.watering.application.command.watering

import com.it_finne.watering.application.command.*
import com.it_finne.watering.application.command.watering.device.Device
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class Watering: Command() {
    init {
        this.name = "watering"
        this.children = arrayOf(
                Device(),
                Humidity(),
                Run(),
                Setting(),
                Switch(),
                Temperature(),
                WaterAmount()
        )
    }

    override fun execute(event: CommandEvent?) {
        // TODO
    }

}