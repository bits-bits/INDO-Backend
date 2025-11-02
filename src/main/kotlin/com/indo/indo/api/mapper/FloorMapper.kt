package com.indo.indo.api.mapper

import com.indo.indo.api.dto.FloorBasicDetails
import com.indo.indo.api.dto.FloorDetailsResponse
import com.indo.indo.api.dto.LocationByFloorDetails
import com.indo.indo.entity.Floor
import org.springframework.stereotype.Component

@Component
class FloorMapper {
	fun toBasicDetails(floor: Floor): FloorBasicDetails =
		FloorBasicDetails(id = floor.id.toString(), number = floor.number)

	fun toFloorDetailsResponse(floor: Floor): FloorDetailsResponse =
		FloorDetailsResponse(
			id = floor.toString(),
			number = floor.number,
			imageUrl = floor.imageUrl,
			locations = floor.locations.sortedBy { it.name }.map {
				LocationByFloorDetails(
					id = it.id.toString(),
					name = it.name,
					aliasName = it.aliasName,
				)
			}
		)
}