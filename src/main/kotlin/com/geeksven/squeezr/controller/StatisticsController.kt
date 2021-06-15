package com.geeksven.squeezr.controller

import com.geeksven.squeezr.model.api.StatisticSqueeze
import com.geeksven.squeezr.model.api.StatisticsResponse
import com.geeksven.squeezr.service.StatisticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.temporal.ChronoUnit

@RestController
class StatisticsController(private val statisticsService: StatisticsService) {

    @GetMapping("/statistics")
    fun getStatistics(): ResponseEntity<StatisticsResponse> {
        val (totalSqueezes, inLast24h) = statisticsService.getTotalNumSqueezesAndSqueezesFromLastDay()
        val inLast24hResponse = inLast24h.map {
            StatisticSqueeze(it.visits
                    .filter { squeezeVisit -> squeezeVisit.created > Instant.now().minus(1, ChronoUnit.DAYS) }
                    .count(), it.slug, it.longUrl, it.shortUrl, it.visits.map { squeezeVisit -> squeezeVisit.created })
        }

        return ResponseEntity.ok(StatisticsResponse(totalSqueezes, inLast24hResponse.size, inLast24hResponse))
    }
}