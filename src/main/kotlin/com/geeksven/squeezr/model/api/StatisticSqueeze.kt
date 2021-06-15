package com.geeksven.squeezr.model.api

import java.time.Instant

data class StatisticSqueeze(val visitsLastDay: Int, val slug: String, val longUrl: String, val shortUrl: String, val visits: List<Instant>)