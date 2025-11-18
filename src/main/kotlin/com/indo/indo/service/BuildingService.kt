package com.indo.indo.service

import com.indo.indo.entity.Building
import com.indo.indo.repository.BuildingRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BuildingService(private val buildingRepository: BuildingRepository) {
	fun findBuildingById(buildingId: UUID): Building? {
		return buildingRepository.findById(buildingId).orElse(null)
	}

	fun getAllBuildings(): List<Building> {
		return buildingRepository.findAll()
	}
}