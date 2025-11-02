package com.indo.indo.api.mapper

import com.indo.indo.api.dto.BuildingDetailsResponse
import com.indo.indo.api.dto.FloorBasicDetails
import com.indo.indo.entity.Building
import org.springframework.stereotype.Component

@Component
class BuildingMapper(private val floorMapper: FloorMapper) {
	fun toBuildingDetailsResponse(building: Building) =
		BuildingDetailsResponse(
			id = building.id.toString(),
			name = building.name,
			imageUrl = building.imageUrl,
			floorsBasicDetails = building.floors.sortedBy { it.number }.map {
				floorMapper.toBasicDetails(it)
			}
		)
}