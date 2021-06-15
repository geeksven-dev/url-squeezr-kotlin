package com.geeksven.squeezr.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("VISITS")
data class SqueezeVisit(@Id val id: Long? = null, val created: Instant)
