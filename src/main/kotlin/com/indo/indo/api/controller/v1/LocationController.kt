package com.indo.indo.api.controller.v1

import com.indo.indo.api.dto.LocationDetailsResponse
import com.indo.indo.api.dto.toLocationDetailsResponse
import com.indo.indo.exception.InvalidIdFormException
import com.indo.indo.exception.LocationNotFoundException
import com.indo.indo.service.LocationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/location")
class LocationController(private val locationService: LocationService) {

    @GetMapping("/by_name")
    fun getLocationsByName(@RequestParam("name") query: String): ResponseEntity<List<LocationDetailsResponse>> {
        val locations = locationService.findLocationsByName(query = query).map { it.toLocationDetailsResponse() }
        return ResponseEntity.ok(locations)
    }

    @GetMapping("/by_building")
    fun getLocationsByBuildingName(@RequestParam("building") query: String): ResponseEntity<List<LocationDetailsResponse>> {
        val locations = locationService.findLocationByBuildingName(buildingName = query).map { it.toLocationDetailsResponse() }
        return ResponseEntity.ok(locations)
    }

    @GetMapping(path = ["/{id}"])
    fun getLocationById(@PathVariable id: String): ResponseEntity<LocationDetailsResponse> {
        checkValidUUid(id = id)
        val location = locationService.findLocationById(locationId = UUID.fromString(id))?.toLocationDetailsResponse()
            ?: throw LocationNotFoundException(message = "No location found with that Id")
        return ResponseEntity.ok(location)
    }

    private fun checkValidUUid(id: String) {
        try {
            UUID.fromString(id)
        } catch (e: Exception) {
            throw InvalidIdFormException(message = " The id you entered is not a valid id")
        }
    }

}