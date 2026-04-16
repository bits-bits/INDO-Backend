package com.indo.indo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.api")
data class ApiProperties(
    var key: String = ""
)