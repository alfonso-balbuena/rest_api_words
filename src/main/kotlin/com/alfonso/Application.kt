package com.alfonso

import io.ktor.server.cio.*
import com.alfonso.plugins.*
import io.ktor.application.*
import io.ktor.features.*

fun main(args: Array<String>) : Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    configureSerialization()
    install(CallLogging)
    configureKoin()
    configureSecurity()
    configureRouting()
}
