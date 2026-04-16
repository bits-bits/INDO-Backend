package com.indo.indo

import com.indo.indo.entity.Coordinate
import com.indo.indo.util.NavigationUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NavigationUtilsDistanceTest {
    private val toleranceMeters: Double = 0.5

    @Test
    fun sameCoordinates_returnZeroDistance() {
        val distance = NavigationUtils.getDistanceBetweenCoordinates(
            Coordinate(30.0444, 31.2357),
            Coordinate(30.0444, 31.2357)
        )

        assertEquals(0.0, distance, toleranceMeters)
    }

    @Test
    fun distanceIsSymmetric() {
        val a = Coordinate(30.0444, 31.2357)
        val b = Coordinate(30.0450, 31.2362)

        val aToB = NavigationUtils.getDistanceBetweenCoordinates(a, b)
        val bToA = NavigationUtils.getDistanceBetweenCoordinates(b, a)

        assertEquals(aToB, bToA, toleranceMeters)
    }

    @Test
    fun smallNorthSouthMovement_hasExpectedDistance() {
        val distance = NavigationUtils.getDistanceBetweenCoordinates(
            Coordinate(30.0000, 31.0000),
            Coordinate(30.0001, 31.0000)
        )

        // ~11.1 meters per 0.0001 latitude
        assertEquals(11.1, distance, 0.5)
    }

    @Test
    fun smallEastWestMovement_respectsLatitudeScaling() {
        val distance = NavigationUtils.getDistanceBetweenCoordinates(
            Coordinate(30.0000, 31.0000),
            Coordinate(30.0000, 31.0001)
        )

        val expected = 11.1 * kotlin.math.cos(Math.toRadians(30.0))
        assertEquals(expected, distance, 0.5)
    }

    @Test
    fun distanceIncreasesWithLargerDeltas() {
        val origin = Coordinate(30.0444, 31.2357)

        val close = Coordinate(30.0445, 31.2358)
        val far = Coordinate(30.0454, 31.2367)

        val smallDistance =
            NavigationUtils.getDistanceBetweenCoordinates(origin, close)

        val largeDistance =
            NavigationUtils.getDistanceBetweenCoordinates(origin, far)

        assertTrue(largeDistance > smallDistance)
    }
}