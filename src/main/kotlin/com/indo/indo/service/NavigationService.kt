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
import kotlin.math.sqrt

@Service
class NavigationService(
    private val outdoorNavigationRepository: OutdoorNavigationRepository,
    private val locationService: LocationService
) {

    @Deprecated("")
    fun getRoute(startCoordinate: Coordinate, endCoordinate: Coordinate): Route {
        return getOutdoorRoute(startCoordinate, endCoordinate)
    }

    fun getTotalRouteAndCheckPointsToLocation(fromCoordinate: Coordinate, locationId: UUID): Pair<Route, Route> {
        val location =
            locationService.findLocationById(locationId)
                ?: throw ResourceNotFoundException("No location found with that Id")

        val entryPoints = location.building.points.filter { it.type == CoordinateType.ENTRY_POINT }
        val chosenEntryPoint = getNearestEntryPoint(fromCoordinate, entryPoints)
        val outdoorRoute = determineOutdoorRouteToLocation(fromCoordinate, chosenEntryPoint)
        val indoorRoute = getIndoorRoute(
            startIndoorCoordinate = Coordinate(
                latitude = chosenEntryPoint.latitude,
                longitude = chosenEntryPoint.longitude
            ),
            destination = location
        )
        val totalRoute = Route(outdoorRoute.coordinates + indoorRoute.coordinates)

        return Pair(totalRoute, indoorRoute)
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
        startIndoorCoordinate: Coordinate,
        destination: Location
    ): Route {
        val checkpoints = destination.building.points
            .filter { it.type == CoordinateType.CHECK_POINT }
            .map { it.toCoordinate() }
            .toMutableList()

        val destinationCoordinate = Coordinate(destination.latitude, destination.longitude)

        if (destination.floor.number == 0){
            val route = mutableListOf<Coordinate>()
            var current = startIndoorCoordinate

            while (checkpoints.isNotEmpty()) {
                val nearest = checkpoints
                    .filter { distanceBetween(it, destinationCoordinate) < distanceBetween(current, destinationCoordinate) }
                    .minByOrNull { distanceBetween(current, it) }
                    ?: break

                route.add(nearest)
                checkpoints.remove(nearest)
                current = nearest
            }

            route.add(destinationCoordinate)

            return Route(listOf(startIndoorCoordinate) + route)
        }else{
            // TODO: logic in case of stairs
            return Route(emptyList())
        }
    }

    private fun distanceBetween(a: Coordinate, b: Coordinate): Double {
        val dx = a.latitude - b.latitude
        val dy = a.longitude - b.longitude
        return sqrt(dx * dx + dy * dy)
    }

    private fun determineOutdoorRouteToLocation(fromCoordinate: Coordinate, entryPoint: Point): Route {

        val projectionPoint =
            entryPoint.projection ?: throw ResourceNotFoundException("No projection found for entry point")
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