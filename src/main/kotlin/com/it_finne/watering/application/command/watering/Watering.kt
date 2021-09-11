package com.it_finne.watering.application.command.watering

import com.it_finne.watering.application.command.authentication.SignIn
import com.it_finne.watering.application.command.authentication.SignUp
import com.it_finne.watering.application.command.device.Device
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class Watering: Command() {
    init {
        this.name = "watering"
        this.children = arrayOf(Device(), SignUp(), SignIn())
    }

    override fun execute(event: CommandEvent) {
        TODO("Not yet implemented")
    }

}