package com.indo.indo.api.controller.v1

import com.indo.indo.api.dto.FloorDetailsResponse
import com.indo.indo.api.mapper.FloorMapper
import com.indo.indo.exception.InvalidIdFormException
import com.indo.indo.exception.ResourceNotFoundException
import com.indo.indo.service.FloorService
import com.indo.indo.util.UUIDUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/floor")
class FloorController(private val floorService: FloorService, private val floorMapper: FloorMapper) {
	@GetMapping("/{id}")
	fun getFloorById(@PathVariable id: String): ResponseEntity<FloorDetailsResponse> {
		if (!UUIDUtils.isValidUUIDString(id)) throw InvalidIdFormException("Invalid floor Id form")

		val floor = floorService.findFloorById(UUID.fromString(id))
			?: throw ResourceNotFoundException("Floor with given Id is not found")

		return ResponseEntity.ok(floorMapper.toFloorDetailsResponse(floor))
	}
}