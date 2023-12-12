package com.example.manchasterunitedsquad.repository

import javax.inject.Singleton
import javax.inject.Inject
import com.example.manchasterunitedsquad.data.PlayerDao
import com.example.manchasterunitedsquad.data.PlayerEntity

@Singleton

class PlayerRepo @Inject constructor(private val playerDao: PlayerDao) {
    fun getAllPlayer() = playerDao.getAllplayer()
    fun getAllFavoritePlayer() = playerDao.getAllFavoriteplayer()
    fun getPlayer(id: Int) = playerDao.getplayer(id)
    fun searchPlayer(query: String) = playerDao.searchplayer(query)
    suspend fun insertAllPlayer(player: List<PlayerEntity>) = playerDao.insertAllplayer(player)
    suspend fun updateFavoritePlayer(id: Int, isFavorite: Boolean) = playerDao.updateFavoriteplayer(id, isFavorite)
}