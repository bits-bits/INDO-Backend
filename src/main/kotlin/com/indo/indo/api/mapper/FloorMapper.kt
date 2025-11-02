package com.indo.indo.api.mapper

import com.indo.indo.api.dto.FloorBasicDetails
import com.indo.indo.entity.Floor
import org.springframework.stereotype.Component

@Component
class FloorMapper {
	fun toBasicDetails(floor: Floor): FloorBasicDetails =
		FloorBasicDetails(id = floor.id.toString(), number = floor.number)
}