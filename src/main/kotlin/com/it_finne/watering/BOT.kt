package com.it_finne.watering

<<<<<<< HEAD
import com.it_finne.watering.application.command.auto_watering.AutoWatering
=======
import com.it_finne.watering.application.command.AutoWatering
>>>>>>> 17ebb819d83a5ac13bcd08bcc6916eff5fc462bb
import com.it_finne.watering.application.command.Prefix
import com.it_finne.watering.application.command.watering.Watering
import com.it_finne.watering.config.Application
import com.it_finne.watering.application.listener.CommandExceptionListener
import com.it_finne.watering.application.listener.GuildJoinListener
import com.it_finne.watering.application.listener.ReadyListener
import com.it_finne.watering.application.setting.GuildSettingsManager
import com.it_finne.watering.lib.aws.AwsParameterStore
import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandClientBuilder
import mu.KLogger
import mu.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val logger: KLogger = KotlinLogging.logger {}

class BOT : KoinComponent{
    private val discordbot: JDA

    init {
        logger.info { "the application has started in '${Application.applicationMode}' mode." }

        val awsParameterStore by inject<AwsParameterStore>()
        val commandClient: CommandClient = CommandClientBuilder()
            .setStatus(OnlineStatus.ONLINE)
            .setActivity(Activity.playing("!help"))
            .setOwnerId(awsParameterStore.getParameter(Application.configuration.discordbot.ownerId))
            .addCommands(
                Prefix(),
<<<<<<< HEAD
                AutoWatering(),
                Watering()
=======
                AutoWatering()
>>>>>>> 17ebb819d83a5ac13bcd08bcc6916eff5fc462bb
            )
            .setGuildSettingsManager(GuildSettingsManager())
            .setEmojis(
                Application.configuration.discordbot.emojis.success,
                Application.configuration.discordbot.emojis.warning,
                Application.configuration.discordbot.emojis.error
            )
            .setListener(CommandExceptionListener())
            .build()

        this.discordbot = JDABuilder
            .createDefault(awsParameterStore.getParameter(Application.configuration.discordbot.token))
            .addEventListeners(
                commandClient,
                GuildJoinListener(),
                ReadyListener()
            )
            .build()
    }
}
