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

    fun getTotalRouteAndCheckPointsToLocation(fromCoordinate: Coordinate, locationId: UUID): Triple<Route, Route, Route> {
        val location =
            locationService.findLocationById(locationId)
                ?: throw ResourceNotFoundException("No location found with that Id")

        val entryPoints = location.building.points.filter { it.type == CoordinateType.ENTRY_POINT }
        val chosenEntryPoint = getNearestEntryPoint(fromCoordinate, entryPoints)
        val outdoorRoute = determineOutdoorRouteToLocation(fromCoordinate, chosenEntryPoint)
        val indoorRoutes = getIndoorRoute(
            startIndoorCoordinate = Coordinate(
                latitude = chosenEntryPoint.latitude,
                longitude = chosenEntryPoint.longitude
            ),
            destination = location
        )
        val firstRoute = Route(outdoorRoute.coordinates + indoorRoutes.first.coordinates)            // outdoor + ground floor
        val secondRoute = indoorRoutes.second ?: Route(emptyList())                     // upper floor
        val usedCheckPoints = indoorRoutes.first
        return Triple(firstRoute, secondRoute, usedCheckPoints)
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
    ): Pair<Route, Route?> {
        val checkpoints = destination.building.points
            .filter { it.type == CoordinateType.CHECK_POINT }
            .map { it.toCoordinate() }
        

        val destinationCoordinate = Coordinate(destination.latitude, destination.longitude)

        if (destination.floor.number == 0) {
            val route = buildRoute(startIndoorCoordinate, destinationCoordinate, checkpoints)
            return Pair(Route(route), null)
        } else {
            val stairs = destination.building.points
                .filter { it.type == CoordinateType.STAIRS }
                .map { it.toCoordinate() }

            // nearest stairs to the destination
            val nearestStairs = stairs.minByOrNull { distanceBetween(it, destinationCoordinate) }
                ?: return Pair(Route(emptyList()), null)

            // route from start -> stairs (ground floor)
            val firstFloorRoute = buildRoute(startIndoorCoordinate, nearestStairs, checkpoints)

            // route from stairs -> destination (upper floor, no checkpoints needed)
            val upperFloorRoute = buildRoute(nearestStairs, destinationCoordinate, checkpoints)

            return Pair(Route(firstFloorRoute), Route(upperFloorRoute))
        }
    }

    private fun buildRoute(
        start: Coordinate,
        destination: Coordinate,
        checkpoints: List<Coordinate>  // no longer MutableList
    ): List<Coordinate> {
        val remaining = checkpoints.toMutableList()  // copy internally
        val route = mutableListOf<Coordinate>()
        var current = start

        while (remaining.isNotEmpty()) {
            val nearest = remaining
                .filter { distanceBetween(it, destination) < distanceBetween(current, destination) }
                .minByOrNull { distanceBetween(current, it) }
                ?: break

            route.add(nearest)
            remaining.remove(nearest)
            current = nearest
        }

        route.add(destination)
        return listOf(start) + route
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