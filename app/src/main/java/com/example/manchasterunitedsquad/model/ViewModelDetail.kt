package com.example.manchasterunitedsquad.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manchasterunitedsquad.data.PlayerEntity
import com.example.manchasterunitedsquad.repository.PlayerRepo
import com.example.manchasterunitedsquad.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ViewModelDetail @Inject constructor(private val playerRepo: PlayerRepo) : ViewModel() {
    private val _player = MutableStateFlow<ResultState<PlayerEntity>>(ResultState.Loading)
    val player = _player.asStateFlow()

    fun getplayer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.getPlayer(id)
                .catch { _player.value = ResultState.Error(it.message.toString()) }
                .collect { _player.value = ResultState.Success(it) }
        }
    }

    fun updateFavoritePlayer(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.updateFavoritePlayer(id, isFavorite)
        }
    }
}