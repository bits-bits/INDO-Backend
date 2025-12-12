package com.indo.indo.entity

import com.indo.indo.repository.outdoorNavigation.RouteResponse

data class Route(
    val coordinates: List<Coordinate>
)

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)

fun RouteResponse.toRoute(): Route {
    println("TAAG in mapper")
    val route = this.features.firstOrNull()?.geometry?.coordinates?.firstOrNull()
    println("TAAG in mapper 2: $route")
    val points: MutableList<Coordinate> = mutableListOf()
    route?.forEach { point->
        val latitude = point[0]
        val longitude = point[1]
        val coordinate = Coordinate(latitude = latitude, longitude = longitude)
        points.add(coordinate)
    }
    return Route(points)
}