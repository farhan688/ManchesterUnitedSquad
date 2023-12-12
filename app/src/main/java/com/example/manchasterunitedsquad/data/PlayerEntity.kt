package com.example.manchasterunitedsquad.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? =null,
    val name: String,
    val description: String,
    val country: String,
    val photo: Int,
    val skillMoves: Double,
    val marketValue: String,
    var isFavorite: Boolean = false,
)