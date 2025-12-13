package com.indo.indo.api.mapper

import com.indo.indo.entity.Coordinate
import com.indo.indo.exception.InvalidFormException

fun String.parseCoordinate(): Coordinate {
    val parts = this.split(",")

    if (parts.size != 2) throw InvalidFormException("Invalid coordinate format")

    val latitude: Double
    val longitude: Double

    try {
        latitude = parts[0].toDouble()
        longitude = parts[1].toDouble()
    } catch (e: NumberFormatException) {
        throw InvalidFormException("Invalid coordinate format, expected decimal value")
    }

    return Coordinate(latitude, longitude)
}