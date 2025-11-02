package com.indo.indo.api.dto

data class FloorDetailsResponse(
	val id: String,
	val number: Int,
	val imageUrl: String?,
	val locations: List<LocationByFloorDetails>
)
