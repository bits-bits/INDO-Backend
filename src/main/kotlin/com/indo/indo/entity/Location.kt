package com.indo.indo.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "locations", schema = "navigation")
class Location(
	@Id
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	val id: UUID = UUID.randomUUID(),

	@Column
	val name: String?,

	@Column
	val aliasName: String?,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "building_id", nullable = false)
	val building: Building,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "floor_id", nullable = false)
	val floor: Floor,

    @Column(nullable = false)
    val latitude: Double,

    @Column(nullable = false)
    val longitude: Double,

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	val createdAt: LocalDateTime,

	@UpdateTimestamp
	@Column(nullable = false)
	val updatedAt: LocalDateTime
)
