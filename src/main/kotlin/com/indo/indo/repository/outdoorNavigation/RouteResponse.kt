package com.indo.indo.repository.outdoorNavigation

data class RouteResponse(
    val features: List<Feature>
)

data class Feature(
    val geometry: Geometry
)

data class Geometry(
    val coordinates: List<List<List<Double>>>
)

