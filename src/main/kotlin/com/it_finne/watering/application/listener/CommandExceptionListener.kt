package com.it_finne.watering.application.listener

import com.it_finne.watering.error.CommandErrorException
import com.it_finne.watering.error.CommandWarningException
import com.it_finne.watering.error.RefreshTokenExpiresException
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.command.CommandListener
import mu.KLogger
import mu.KotlinLogging

class CommandExceptionListener(
    private val logger: KLogger = KotlinLogging.logger {}
): CommandListener {
    override fun onCommandException(event: CommandEvent, command: Command, throwable: Throwable) {
        when (throwable) {
            is CommandErrorException -> {
                event.replyError(throwable.message)
            }
            is CommandWarningException -> {
                event.replyWarning(throwable.message)
            }
            is RefreshTokenExpiresException -> {
                event.replyWarning((throwable.message))
            }
            else -> {
                logger.error(throwable) { "An exception occurred in a command. command=${command.name}, guild=${event.guild.id}" }
                event.replyError("An unknown error has occurred. Please try again later.")
            }
        }
    }
}
