package com.indo.indo.service

import com.indo.indo.entity.Coordinate
import com.indo.indo.entity.Route
import com.indo.indo.repository.outdoorNavigation.OutdoorNavigationRepository
import org.springframework.stereotype.Service

@Service
class NavigationService(
    private val outdoorNavigationRepository: OutdoorNavigationRepository,
    private val locationService: LocationService
) {

    fun getRouteToLocation(): Route {
        // locationId -> location -> coordinates, entry-point to building(door)
        return getTotalRoute(
            startDestination = Coordinate(
                latitude = 31.208112699082903,
                longitude = 29.922827843407845
            ), endDestination = Coordinate(latitude = 31.206379104112784, longitude = 29.924302984554174)
        )
    }

    private fun getTotalRoute(startDestination: Coordinate, endDestination: Coordinate): Route {
        // endDestination -> projection on street = new end destination
        // startDestination -> start projection, endDestination -> end projection
        val startProjection = startDestination
        val endProjection = endDestination
        val outdoorRoute =
            getOutdoorRoute(startStreetDestination = startProjection, endStreetDestination = endProjection)
        val indoorRoute = getIndoorRoute(startProjection, endProjection)
        return Route(coordinates = outdoorRoute.coordinates + indoorRoute.coordinates)
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
}


// start        end
// indoor       indoor              no outdoor
// indoor       outdoor
// outdoor      indoor
// outdoor      outdoor             no indoor