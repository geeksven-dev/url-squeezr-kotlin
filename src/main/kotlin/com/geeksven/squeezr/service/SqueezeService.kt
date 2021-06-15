package com.geeksven.squeezr.service

import com.geeksven.squeezr.ServerProperties
import com.geeksven.squeezr.model.Squeeze
import com.geeksven.squeezr.model.SqueezeVisit
import com.geeksven.squeezr.model.api.SqueezeCreateRequest
import com.geeksven.squeezr.repository.SqueezeRepository
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class SqueezeService(val squeezeRepository: SqueezeRepository, val serverProperties: ServerProperties) {

    fun squeezeUrl(squeezeCreateRequest: SqueezeCreateRequest): Squeeze {
        val slug = RandomStringUtils.randomAlphanumeric(6)
        return squeezeRepository.save(Squeeze(
                slug = squeezeCreateRequest.customSlug ?: slug,
                longUrl = squeezeCreateRequest.fullUrl,
                shortUrl = squeezeCreateRequest.customSlug?.let { "${serverProperties.url}/$it" } ?: "${serverProperties.url}/$slug",
                visits = mutableSetOf(),
                created = Instant.now()))
    }

    fun findUrlForSlugAndTrackVisit(slug: String): String = squeezeRepository.findBySlug(slug)
            .let {
                it.visits.add(SqueezeVisit(created = Instant.now()))
                squeezeRepository.save(it)
                it.longUrl
            }

}