package com.indo.indo.repository

import com.indo.indo.entity.Floor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FloorRepository : JpaRepository<Floor, UUID> {
}