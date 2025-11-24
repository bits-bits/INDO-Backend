package com.indo.indo.api.mapper

import com.indo.indo.api.dto.FloorDto
import com.indo.indo.api.dto.FloorDetailsDto
import com.indo.indo.api.dto.LocationDto
import com.indo.indo.entity.Floor

fun Floor.toBasicDetails(): FloorDto {
	return FloorDto(
		id = this.id.toString(),
		number = this.number,
		imageUrl = this.imageUrl
	)
}

fun Floor.toDetailsResponse(): FloorDetailsDto {
	return FloorDetailsDto(
		id = this.id.toString(),
		number = this.number,
		imageUrl = this.imageUrl,
		locations = this.locations.sortedBy { it.name }.map { floor ->
			LocationDto(
				id = floor.id.toString(),
				name = floor.name,
				aliasName = floor.aliasName,
			)
		}
	)
}