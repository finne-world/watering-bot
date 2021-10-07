package com.it_finne.watering.config

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import com.it_finne.watering.application.constant.ApplicationMode
import com.it_finne.watering.application.http.HttpClientImpl
import com.it_finne.watering.application.http.HttpClient
import com.it_finne.watering.error.IllegalEnvironmentException
import com.it_finne.watering.lib.aws.AwsParameterStore
import com.it_finne.watering.lib.aws.AwsParameterStoreImpl
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import mu.KLogger
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.slf4j.LoggerFactory
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val logger: KLogger = KotlinLogging.logger {}
private const val ENVIRONMENT_NAME = "ENVIRONMENT"
private const val ENVIRONMENT_DEFAULT_VALUE = "development"

object Application : KoinComponent {
    val applicationMode: ApplicationMode
    val configuration: Configuration

    init {
        this.koinSetUp()
        this.applicationMode = this.getApplicationModeFromEnvironmentVariable()
        this.configureLogging(this.applicationMode)
        this.configuration = Configuration.load(applicationMode)
        this.connectDatabase()
    }

    private fun configureLogging(applicationMode: ApplicationMode) {
        val loggerContext: LoggerContext = (LoggerFactory.getILoggerFactory() as LoggerContext).also {
            it.reset()
        }

        val filename = "/logback-${applicationMode.value}.xml"
        JoranConfigurator().also {
            it.context = loggerContext
            it.doConfigure(Application::class.java.getResource(filename))
        }
    }

    private fun getApplicationModeFromEnvironmentVariable(): ApplicationMode {
        val environmentValue: String = System.getenv(ENVIRONMENT_NAME) ?: ENVIRONMENT_DEFAULT_VALUE.also { logger.warn { "Application environment is not specified. configure environment default value `development`." } }

        return ApplicationMode.values().find { it.value == environmentValue.lowercase() } ?: throw IllegalEnvironmentException(environmentValue)
    }

    private fun connectDatabase() {
        val awsParameterStore by inject<AwsParameterStore>()
        val hikariConfig: HikariConfig = HikariConfig().also {
            it.jdbcUrl = "jdbc:${this.configuration.database.vendor}://${this.configuration.database.host}/${this.configuration.database.database}?serverTimezone=${this.configuration.database.timezone}"
            it.driverClassName = this.configuration.database.driver
            it.username = awsParameterStore.getParameter(this.configuration.database.user)
            it.password = awsParameterStore.getParameter(this.configuration.database.password)
            it.maximumPoolSize = this.configuration.database.poolSize
        }

        Database.connect(HikariDataSource(hikariConfig))
    }

    private fun koinSetUp() {
        val utilitiesModule = module {
            single { HttpClientImpl() as HttpClient }
            single { AwsParameterStoreImpl() as AwsParameterStore }
        }

        startKoin {
            modules(utilitiesModule)
        }
    }
}
