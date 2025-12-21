package com.indo.indo.entity

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "points", schema = "navigation")
class Point(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val latitude: Double,

    @Column(nullable = false)
    val longitude: Double,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: CoordinateType,

    @OneToOne
    @JoinColumn(nullable = true, name = "projection_point_id")
    val projection: Point?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    val building: Building,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: LocalDateTime
)

enum class CoordinateType(@JsonValue val pointName: String){
    ENTRY_POINT(pointName = "entryPoint"),
    CHECK_POINT(pointName = "checkPoint"),
    STAIRS(pointName = "stairs"),
    DESTINATION(pointName = "destination"),
    PROJECTION(pointName = "projection")
}

fun Point.toCoordinate() = Coordinate(this.latitude, this.longitude)