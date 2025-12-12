package com.indo.indo.repository.outdoorNavigation

import com.indo.indo.entity.Route

interface OutdoorNavigationRepository {
    fun getRoute(
        startPointLatitude: Double,
        startPointLongitude: Double,
        endPointLatitude: Double,
        endPointLongitude: Double
    ): Route
}