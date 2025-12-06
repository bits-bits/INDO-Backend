package com.indo.indo.api.dto

data class BuildingDetailsDto(
	val id: String,
	val name: String,
	val imageUrl: String?,
	val floorsBasicDetails: List<FloorDto>,
)
