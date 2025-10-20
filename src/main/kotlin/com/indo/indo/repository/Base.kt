package com.indo.indo.repository

import com.indo.indo.entity.Location
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LocationRepository : JpaRepository<Location, UUID>{
    fun findByNameContainingIgnoreCase(query: String): List<Location>
    fun findByAliasNameContainingIgnoreCase(query: String): List<Location>
    fun findByBuilding_NameContainingIgnoreCase(query: String): List<Location>
}