package com.indo.indo.api.mapper

import com.indo.indo.api.dto.BuildingDetailsResponse
import com.indo.indo.entity.Building

fun Building.toBuildingDetailsResponse() =
	BuildingDetailsResponse(
		id = this.id.toString(),
		name = this.name,
		imageUrl = this.imageUrl,
		floorsBasicDetails = this.floors.sortedBy { it.number }.map {
			it.toBasicDetails()
		}
	)
