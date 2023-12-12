package com.example.manchasterunitedsquad.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manchasterunitedsquad.data.PlayerEntity
import com.example.manchasterunitedsquad.data.UnitedPlayerData
import com.example.manchasterunitedsquad.repository.PlayerRepo
import com.example.manchasterunitedsquad.ui.page.home.HomeUiState
import com.example.manchasterunitedsquad.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ViewModelHome  @Inject constructor(private val playerRepo: PlayerRepo) : ViewModel() {
    private val _allplayer = MutableStateFlow<ResultState<List<PlayerEntity>>>(ResultState.Loading)
    val allplayer = _allplayer.asStateFlow()

    private val _homeUiState = mutableStateOf(HomeUiState())
    val homeUiState: State<HomeUiState> = _homeUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.getAllPlayer().collect { player ->
                when (player.isEmpty()) {
                    true -> playerRepo.insertAllPlayer(UnitedPlayerData.dummy)
                    else -> _allplayer.value = ResultState.Success(player)
                }
            }
        }
    }

    private fun searchplayer(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.searchPlayer(query)
                .catch { _allplayer.value = ResultState.Error(it.message.toString()) }
                .collect { _allplayer.value = ResultState.Success(it) }
        }
    }

    fun updateFavoriteplayer(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.updateFavoritePlayer(id, isFavorite)
        }
    }

    fun onQueryChange(query: String) {
        _homeUiState.value = _homeUiState.value.copy(query = query)
        searchplayer(query)
    }
}