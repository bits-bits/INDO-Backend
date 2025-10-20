package com.indo.indo.service

import com.indo.indo.entity.Location
import com.indo.indo.repository.LocationRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LocationService(private val locationRepository: LocationRepository) {
    fun findLocationsByName(query: String): List<Location> {
        val locationsByName = locationRepository.findByNameContainingIgnoreCase(query)
        val locationsByAliasName = locationRepository.findByAliasNameContainingIgnoreCase(query)
        return locationsByName + locationsByAliasName
    }

    fun findLocationById(locationId: UUID): Location? {
        return locationRepository.findById(locationId).orElse(null)
    }

    fun findLocationByBuildingName(buildingName: String): List<Location> {
        return locationRepository.findByBuilding_NameContainingIgnoreCase(query = buildingName)
    }
}