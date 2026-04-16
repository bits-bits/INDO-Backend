package com.indo.indo.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp


@Entity
@Table(name = "buildings", schema = "navigation")
class Building(
	@Id
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	val id: UUID = UUID.randomUUID(),

	@Column(nullable = false)
	val name: String,

	@Column
	val imageUrl: String?,

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
	val floors: MutableList<Floor> = mutableListOf(),

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
	val locations: MutableList<Location> = mutableListOf(),

	@OneToMany(
		mappedBy = "building",
		fetch = FetchType.LAZY,
		cascade = [CascadeType.ALL],
		orphanRemoval = true
	)
	val points: MutableList<Point> = mutableListOf(),

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	val createdAt: LocalDateTime,

	@UpdateTimestamp
	@Column(nullable = false)
	val updatedAt: LocalDateTime
)
