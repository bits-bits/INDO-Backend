package com.indo.indo.api.dto

import com.indo.indo.entity.Location

fun Location.toLocationDetailsResponse(): LocationDetailsResponse{
    return LocationDetailsResponse(
        id = this.id.toString(),
        name = this.name,
        aliasName = this.aliasName,
        buildingName = this.building.name,
        floorNumber = this.floor.number
    )
}