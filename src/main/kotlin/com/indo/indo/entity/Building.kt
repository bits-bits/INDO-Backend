package com.indo.indo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "buildings", schema = "navigation")
data class Building(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column
    val imageUrl: String?,

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    val floors: List<Floor> = emptyList(),

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    val locations: List<Location> = emptyList()
)
