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

class ViewModelFavorite @Inject constructor(private val playerRepo: PlayerRepo) : ViewModel() {
    private val _allFavoritePlayer = MutableStateFlow<ResultState<List<PlayerEntity>>>(ResultState.Loading)
    val allFavoritePlayer = _allFavoritePlayer.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.getAllFavoritePlayer()
                .catch { _allFavoritePlayer.value = ResultState.Error(it.message.toString()) }
                .collect { _allFavoritePlayer.value = ResultState.Success(it) }
        }
    }

    fun updateFavoritePlayer(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.updateFavoritePlayer(id, isFavorite)
        }
    }
}