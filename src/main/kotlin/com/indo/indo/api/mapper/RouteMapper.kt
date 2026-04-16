package com.indo.indo.api.mapper

import com.indo.indo.api.dto.CoordinateDto
import com.indo.indo.api.dto.RouteDto
import com.indo.indo.entity.Coordinate
import com.indo.indo.entity.CoordinateType
import com.indo.indo.entity.Route

fun Route.toRouteDto(): RouteDto {
    return RouteDto(
        coordinates = this.coordinates.map { it.toCoordinateDto() }
    )
}

fun Coordinate.toCoordinateDto(): CoordinateDto {
    return CoordinateDto(
        latitude = this.latitude,
        longitude = this.longitude,
        type = CoordinateType.CHECK_POINT
    )
}