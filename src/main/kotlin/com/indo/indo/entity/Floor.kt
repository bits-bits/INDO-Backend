package com.indo.indo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
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

    @OneToMany(mappedBy = "floor", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val locations: MutableList<Location> = mutableListOf(),

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: LocalDateTime
)
