package com.indo.indo.api.controller.v1

import com.indo.indo.api.dto.RouteDto
import com.indo.indo.api.mapper.parseCoordinate
import com.indo.indo.api.mapper.toRouteDto
import com.indo.indo.exception.InvalidFormException
import com.indo.indo.service.NavigationService
import com.indo.indo.util.UUIDUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/route")
class NavigationController(private val navigationService: NavigationService) {

    @GetMapping("/coordinates")
    fun getRoute(@RequestParam("from") from: String, @RequestParam("to") to: String): ResponseEntity<RouteDto> {
        val fromCoordinate = from.parseCoordinate()
        val toCoordinate = to.parseCoordinate()

        val route = navigationService.getRoute(fromCoordinate, toCoordinate).toRouteDto()
        return ResponseEntity.ok(route)
    }

    @GetMapping("/location")
    fun getRouteToLocation(
        @RequestParam("from") from: String,
        @RequestParam("toLocationId") toLocationId: String
    ): ResponseEntity<List<RouteDto>> {
        val fromCoordinate = from.parseCoordinate()
        val locationId =
            UUIDUtils.getUuidOrNull(toLocationId) ?: throw InvalidFormException("Invalid coordinate format")

        val route = navigationService.getTotalRouteAndCheckPointsToLocation(fromCoordinate, locationId).first.toRouteDto()
        val indoorRoute = navigationService.getTotalRouteAndCheckPointsToLocation(fromCoordinate, locationId).second.toRouteDto()
        val response = listOf(route,indoorRoute)
        return ResponseEntity.ok(response)
    }
}