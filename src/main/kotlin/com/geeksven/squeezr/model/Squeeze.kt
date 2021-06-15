package com.geeksven.squeezr.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("SQUEEZE")
data class Squeeze(@Id val id: Long? = null, val slug: String, val longUrl: String, val shortUrl: String, val visits: MutableSet<SqueezeVisit>, val created: Instant)