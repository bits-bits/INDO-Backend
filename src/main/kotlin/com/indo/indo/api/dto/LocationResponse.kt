package com.indo.indo.api.dto

import com.fasterxml.jackson.core.sym.Name

data class LocationDetailsResponse(
    val id: String,
    val name: String?,
    val aliasName: String?,
    val buildingName: String,
    val floorNumber: Int,
)