package com.indo.indo.api.controller.v1

import com.indo.indo.api.dto.RouteDto
import com.indo.indo.api.mapper.toRouteDto
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
    fun getRoute(@RequestParam("latitude") latitude: Double, @RequestParam("longitude") longitude: Double): ResponseEntity<RouteDto> {
        val route = navigationService.getRouteToLocation().toRouteDto()
        return ResponseEntity.ok(route)
    }

}