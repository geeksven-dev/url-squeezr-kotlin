package com.geeksven.squeezr.service

import com.geeksven.squeezr.model.Squeeze
import com.geeksven.squeezr.repository.SqueezeRepository
import org.springframework.stereotype.Service

@Service
class StatisticsService(private val squeezeRepository: SqueezeRepository) {

    fun getTotalNumSqueezesAndSqueezesFromLastDay(): Pair<Int, List<Squeeze>> = Pair(squeezeRepository.count().toInt(), squeezeRepository.findByVisitsInLast24h())

}