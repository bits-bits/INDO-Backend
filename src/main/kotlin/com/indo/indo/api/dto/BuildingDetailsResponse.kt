package com.indo.indo.api.dto

import com.indo.indo.entity.Floor

data class BuildingDetailsResponse(
	val id: String,
	val name: String,
	val imageUrl: String?,
	val floorsBasicDetails: List<FloorBasicDetails>,
)
