package com.indo.indo.api.controller.v1

import com.indo.indo.api.dto.RouteDto
import com.indo.indo.api.mapper.parseCoordinate
import com.indo.indo.api.mapper.toRouteDto
import com.indo.indo.entity.Coordinate
import com.indo.indo.exception.InvalidFormException
import com.indo.indo.service.NavigationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/route")
class NavigationController(private val navigationService: NavigationService) {

    @GetMapping
    fun getRoute(@RequestParam("from") from:String, @RequestParam("to") to:String): ResponseEntity<RouteDto> {
        val fromCoordinate = from.parseCoordinate()
        val toCoordinate = to.parseCoordinate()

        val route = navigationService.getRoute(fromCoordinate, toCoordinate).toRouteDto()
        return ResponseEntity.ok(route)
    }
}