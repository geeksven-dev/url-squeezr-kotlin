package com.geeksven.squeezr.controller

import com.geeksven.squeezr.model.api.SqueezeCreateRequest
import com.geeksven.squeezr.model.api.SqueezeCreateResponse
import com.geeksven.squeezr.service.SqueezeService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping
class SqueezeController(val squeezeService: SqueezeService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/squeeze")
    fun squeeze(@Valid @RequestBody squeezeCreateRequest: SqueezeCreateRequest): ResponseEntity<SqueezeCreateResponse> = squeezeService.squeezeUrl(squeezeCreateRequest)
            .apply { logger.info { "Short request $squeezeCreateRequest -> ${this.shortUrl}" } }
            .let {
                ResponseEntity.created(ServletUriComponentsBuilder.fromHttpUrl(it.shortUrl)
                        .buildAndExpand()
                        .toUri())
                        .body(SqueezeCreateResponse("Successfully shortened URL", it.longUrl, it.shortUrl))
            }

    @GetMapping("/{slug}")
    fun redirectToLongUrl(@PathVariable("slug") slug: String, httpServletResponse: HttpServletResponse) = httpServletResponse.sendRedirect(squeezeService.findUrlForSlugAndTrackVisit(slug))

}