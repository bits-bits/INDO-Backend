package com.indo.indo.service

import com.indo.indo.entity.Coordinate
import com.indo.indo.entity.CoordinateType
import com.indo.indo.entity.Location
import com.indo.indo.entity.Point
import com.indo.indo.entity.Route
import com.indo.indo.entity.toCoordinate
import com.indo.indo.exception.ResourceNotFoundException
import com.indo.indo.repository.outdoorNavigation.OutdoorNavigationRepository
import com.indo.indo.util.NavigationUtils
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class NavigationService(
    private val outdoorNavigationRepository: OutdoorNavigationRepository,
    private val locationService: LocationService
) {

    fun getRoute(startCoordinate: Coordinate, endCoordinate: Coordinate): Route {
        return getOutdoorRoute(startCoordinate, endCoordinate)
    }

    fun getRouteToLocation(fromCoordinate: Coordinate, locationId: UUID): Route {
        val location =
            locationService.findLocationById(locationId)
                ?: throw ResourceNotFoundException("No location found with that Id")

        // TODO indoor route should be added in future
        val outdoorRoute = determineOutdoorRouteToLocation(fromCoordinate, location)
        val indoorRoute = Route(listOf())
        val totalRoute = Route(outdoorRoute.coordinates + indoorRoute.coordinates)

        return totalRoute
    }

    private fun getOutdoorRoute(
        startStreetDestination: Coordinate,
        endStreetDestination: Coordinate
    ): Route {
        return outdoorNavigationRepository.getRoute(
            startPointLatitude = startStreetDestination.latitude,
            startPointLongitude = startStreetDestination.longitude,
            endPointLatitude = endStreetDestination.latitude,
            endPointLongitude = endStreetDestination.longitude
        )
    }

    private fun getIndoorRoute(
        startIndoorDestination: Coordinate?,
        endIndoorDestination: Coordinate?
    ): Route {
        return Route(emptyList())
    }

    private fun determineOutdoorRouteToLocation(fromCoordinate: Coordinate, location: Location): Route {
        val entryPoints = location.building.points.filter { it.type == CoordinateType.ENTRY_POINT }

        val chosenEntryPoint = getNearestEntryPoint(fromCoordinate, entryPoints)

        val projectionPoint =
            chosenEntryPoint.projection ?: throw ResourceNotFoundException("No projection found for entry point")
        val projectionPointCoordinate = projectionPoint.toCoordinate()

        return getOutdoorRoute(fromCoordinate, projectionPointCoordinate)
    }

    private fun getNearestEntryPoint(fromCoordinate: Coordinate, entryPoints: List<Point>): Point {
        return entryPoints.minByOrNull {
            NavigationUtils.getDistanceBetweenCoordinates(
                fromCoordinate,
                it.toCoordinate()
            )
        } ?: throw ResourceNotFoundException("No entry point was found")
    }
}


// start        end
// indoor       indoor              no outdoor
// indoor       outdoor
// outdoor      indoor
// outdoor      outdoor             no indoor