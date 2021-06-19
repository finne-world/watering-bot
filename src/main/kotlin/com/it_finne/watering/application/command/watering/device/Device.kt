package com.it_finne.watering.application.command.watering.device

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class Device: Command() {
    init {
        this.name = "device"
        this.children = arrayOf(Add())
    }

    override fun execute(event: CommandEvent?) {
        TODO("Not yet implemented")
    }
}