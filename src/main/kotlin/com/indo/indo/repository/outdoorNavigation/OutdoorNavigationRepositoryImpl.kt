package com.indo.indo.repository.outdoorNavigation

import com.indo.indo.config.ApiProperties
import com.indo.indo.entity.Route
import com.indo.indo.entity.toRoute
import com.indo.indo.exception.ResourceNotFoundException
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Repository
import org.springframework.web.client.getForEntity

@Repository
class OutdoorNavigationRepositoryImpl(
    restTemplateBuilder: RestTemplateBuilder, private val apiProps: ApiProperties
) :
    OutdoorNavigationRepository {
    private val restTemplate = restTemplateBuilder.build()

    private fun makeRequest(url: String): RouteResponse {
        println("TAAG URL being sent: $url")
        println("TAAG in make request 1")
        val response = restTemplate.getForEntity<RouteResponse>(url)
        println("TAAG in make request: response is $response")

        if (!response.statusCode.is2xxSuccessful) {
            throw ResourceNotFoundException("Weather API error: ${response.statusCode}")
        }

        return response.body ?: throw ResourceNotFoundException("Empty weather response")

    }

    override fun getRoute(
        startPointLatitude: Double,
        startPointLongitude: Double,
        endPointLatitude: Double,
        endPointLongitude: Double
    ): Route {
        val url =
            ("https://api.geoapify.com/v1/routing?" +
                    "waypoints=$startPointLatitude,$startPointLongitude|$endPointLatitude,$endPointLongitude&" +
                    "mode=walk&" +
                    "apiKey=${apiProps.key}")
        return makeRequest(url = url).toRoute()
    }
}

