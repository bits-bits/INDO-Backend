package com.indo.indo.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException::class)
	fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<Map<String, String>> {
		return ResponseEntity.status(404).body(mapOf("error" to "${ex.message}"))
	}

	@ExceptionHandler(InvalidIdFormException::class)
	fun handleInvalidIdForm(ex: InvalidIdFormException): ResponseEntity<Map<String, String>> {
		return ResponseEntity.status(400).body(mapOf("error" to "${ex.message}"))
	}

	@ExceptionHandler(InvalidFormException::class)
	fun handleInvalidForm(ex: InvalidFormException): ResponseEntity<Map<String, String>> {
		return ResponseEntity.status(400).body(mapOf("error" to "${ex.message}"))
	}

	@ExceptionHandler(Exception::class)
	fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
		return ResponseEntity.status(500).body(mapOf("error" to "Internal Server Error"))
	}
}