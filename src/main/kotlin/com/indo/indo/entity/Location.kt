package com.indo.indo.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "locations", schema = "navigation")
data class Location (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
)
