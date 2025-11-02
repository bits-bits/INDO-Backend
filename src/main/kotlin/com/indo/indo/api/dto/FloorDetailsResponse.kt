package com.indo.indo.api.dto

import com.indo.indo.entity.Location

data class FloorDetailsResponse(
	val id: String,
	val number: Int,
	val imageUrl: String?,
	val locations: List<LocationByFloorDetails>
)
