package com.indo.indo.api.dto

import com.fasterxml.jackson.annotation.JsonValue
import com.indo.indo.entity.CoordinateType

data class RouteDto(
    val coordinates: List<CoordinateDto>
)

data class CoordinateDto(
    val latitude: Double,
    val longitude: Double,
    val type: CoordinateType
)