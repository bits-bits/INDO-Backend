package com.indo.indo

import com.indo.indo.config.ApiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApiProperties::class)
class IndoApplication

fun main(args: Array<String>) {
    runApplication<IndoApplication>(*args)
}
