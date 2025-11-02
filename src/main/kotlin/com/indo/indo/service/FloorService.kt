package com.indo.indo.service

import com.indo.indo.entity.Floor
import com.indo.indo.repository.FloorRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FloorService(private val floorRepository: FloorRepository) {
	fun findFloorById(floorId: UUID): Floor? {
		return floorRepository.findById(floorId).orElse(null)
	}
}