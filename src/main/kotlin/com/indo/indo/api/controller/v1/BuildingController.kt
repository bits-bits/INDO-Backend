package com.indo.indo.api.controller.v1

import com.indo.indo.api.dto.BuildingDto
import com.indo.indo.api.dto.BuildingDetailsDto
import com.indo.indo.api.mapper.toBuildingBasicDetails
import com.indo.indo.api.mapper.toBuildingDetailsResponse
import com.indo.indo.exception.InvalidIdFormException
import com.indo.indo.exception.ResourceNotFoundException
import com.indo.indo.service.BuildingService
import com.indo.indo.util.UUIDUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/building")
class BuildingController(private val buildingService: BuildingService) {
	@GetMapping("/{id}")
	fun getBuildingById(@PathVariable id: String): ResponseEntity<BuildingDetailsDto> {
		val uuid = UUIDUtils.getUuidOrNull(id) ?: throw InvalidIdFormException("Invalid building Id form")

		val building = buildingService.findBuildingById(uuid)
			?: throw ResourceNotFoundException("Building with given Id is not found")

		return ResponseEntity.ok(building.toBuildingDetailsResponse())
	}

	@GetMapping("/all")
	fun getAllBuildings(): ResponseEntity<List<BuildingDto>> {
		return ResponseEntity.ok(
			buildingService.getAllBuildings().map { building -> building.toBuildingBasicDetails() })
	}
}