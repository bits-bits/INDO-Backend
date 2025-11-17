package com.indo.indo.api.mapper

import com.indo.indo.api.dto.FloorBasicDetails
import com.indo.indo.api.dto.FloorDetailsResponse
import com.indo.indo.api.dto.LocationByFloorDetails
import com.indo.indo.entity.Floor
import org.springframework.stereotype.Component

fun Floor.toBasicDetails(): FloorBasicDetails {
	return FloorBasicDetails(
		id = this.id.toString(),
		number = this.number
	)
}

fun Floor.toDetailsResponse(): FloorDetailsResponse {
	return FloorDetailsResponse(
		id = this.id.toString(),
		number = this.number,
		imageUrl = this.imageUrl,
		locations = this.locations.sortedBy { it.name }.map {
			LocationByFloorDetails(
				id = it.id.toString(),
				name = it.name,
				aliasName = it.aliasName,
			)
		}
	)
}