package com.indo.indo.util

import com.indo.indo.entity.Coordinate
import com.indo.indo.entity.toRad
import kotlin.math.cos
import kotlin.math.sqrt

class NavigationUtils {
    companion object {
        const val EARTH_RADIUS_METERS = 6_378_137.0

        fun getDistanceBetweenCoordinates(coordinate1: Coordinate, coordinate2: Coordinate): Double {
            val coordinate1InRad = coordinate1.toRad()
            val coordinate2InRad = coordinate2.toRad()

            val deltaLat = coordinate2InRad.latitude - coordinate1InRad.latitude
            val deltaLon = coordinate2InRad.longitude - coordinate1InRad.longitude

            val x = deltaLon * cos((coordinate1InRad.latitude + coordinate2InRad.latitude) / 2)
            val y = deltaLat

            return sqrt(x * x + y * y) * EARTH_RADIUS_METERS
        }
    }
}