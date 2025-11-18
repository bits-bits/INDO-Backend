package com.indo.indo.api.mapper

import com.indo.indo.api.dto.BuildingDto
import com.indo.indo.api.dto.BuildingDetailsDto
import com.indo.indo.entity.Building

fun Building.toBuildingDetailsResponse() =
	BuildingDetailsDto(
		id = this.id.toString(),
		name = this.name,
		imageUrl = this.imageUrl,
		floorsBasicDetails = this.floors.sortedBy { it.number }.map { floor ->
			floor.toBasicDetails()
		}
	)

fun Building.toBuildingBasicDetails(): BuildingDto {
	return BuildingDto(
		id = this.id.toString(),
		name = this.name,
		imageUrl = this.imageUrl,
	)
}
