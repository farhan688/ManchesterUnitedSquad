package com.example.manchasterunitedsquad.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAllplayer(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE isFavorite = 1")
    fun getAllFavoriteplayer(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player WHERE id = :id")
    fun getplayer(id: Int): Flow<PlayerEntity>

    @Query("SELECT * FROM player WHERE name LIKE '%' || :query || '%'")
    fun searchplayer(query: String): Flow<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllplayer(playerList: List<PlayerEntity>)

    @Query("UPDATE player SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteplayer(id: Int, isFavorite: Boolean)
}