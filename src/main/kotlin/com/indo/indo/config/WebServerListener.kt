package com.indo.indo.config

import com.indo.indo.util.ConsoleColor
import com.indo.indo.util.colorize
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class WebServerListener {
    @EventListener
    fun onWebServerReady(event: WebServerInitializedEvent) {
        val port = event.webServer.port
        println("=============================================================")
        println("Server is listening on port: " + "$port".colorize(ConsoleColor.GREEN))
        println("Application is ready to service requests.")
        println("=============================================================")
    }
}