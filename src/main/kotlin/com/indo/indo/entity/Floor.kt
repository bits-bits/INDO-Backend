package com.indo.indo.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "floors", schema = "navigation")
data class Floor(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val number: Int,

    @Column(nullable = true)
    val imageUrl: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    val building: Building,
)
