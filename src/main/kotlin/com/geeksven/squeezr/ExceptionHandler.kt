package com.geeksven.squeezr

import com.geeksven.squeezr.model.api.SqueezeError
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest


@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [(EmptyResultDataAccessException::class)])
    fun handleDataNotFoundException(emptyResultDataAccessException: EmptyResultDataAccessException, webRequest: WebRequest): ResponseEntity<SqueezeError> {
        return ResponseEntity.badRequest().body(SqueezeError("Shortcode not found: ${(webRequest as ServletWebRequest).request.requestURI.removePrefix("/")}", emptyList()))
    }

    @ExceptionHandler(value = [(MethodArgumentNotValidException::class)])
    fun handleValidationException(methodArgumentNotValidException: MethodArgumentNotValidException): ResponseEntity<SqueezeError> {
        return ResponseEntity.badRequest().body(SqueezeError("Validation Failed", methodArgumentNotValidException.fieldErrors
                .map { fieldError -> "${fieldError.field} ${fieldError.defaultMessage}" }))
    }

    @ExceptionHandler(value = [(HttpMessageNotReadableException::class)])
    fun handleWrongFormatForInput(httpMessageNotReadableException: HttpMessageNotReadableException): ResponseEntity<SqueezeError> {
        return ResponseEntity.badRequest().body(SqueezeError("Validation Failed", listOf("Check your POST body for correct data types.")))
    }

    @ExceptionHandler(value = [(DbActionExecutionException::class)])
    fun handleDatabaseException(dbActionExecutionException: DbActionExecutionException): ResponseEntity<SqueezeError> = when (dbActionExecutionException.cause) {
        is DuplicateKeyException -> ResponseEntity.badRequest().body(SqueezeError("The slug already exists", emptyList()))
        is DataIntegrityViolationException -> ResponseEntity.badRequest().body(SqueezeError("The slug is not okay. It must be string and contain 6 chars max.", emptyList()))
        else -> ResponseEntity.badRequest().body(SqueezeError("Unexpected DB error", listOf("Contact the site admin.")))

    }
}