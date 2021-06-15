package com.geeksven.squeezr

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.server", ignoreUnknownFields = true)
data class ServerProperties(val url: String)
