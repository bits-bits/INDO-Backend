package com.indo.indo.api.dto

data class FloorDetailsDto(
	val id: String,
	val number: Int,
	val imageUrl: String?,
	val locations: List<LocationDto>
)
