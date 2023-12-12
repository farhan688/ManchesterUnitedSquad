package com.example.manchasterunitedsquad.ui.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.manchasterunitedsquad.data.PlayerEntity
import com.example.manchasterunitedsquad.model.ViewModelHome
import com.example.manchasterunitedsquad.ui.component.AvailableView
import com.example.manchasterunitedsquad.ui.component.EmptyView
import com.example.manchasterunitedsquad.ui.component.ErrorView
import com.example.manchasterunitedsquad.ui.component.Loader
import com.example.manchasterunitedsquad.ui.component.SearchBar
import com.example.manchasterunitedsquad.utils.ResultState

@Composable
fun HomePage(navController: NavController, scaffoldState: ScaffoldState) {
    val viewModelHome = hiltViewModel<ViewModelHome>()
    val homeUiState by viewModelHome.homeUiState

    viewModelHome.allplayer.collectAsState(ResultState.Loading).value.let { resultState ->
        when (resultState) {
            is ResultState.Loading -> Loader()
            is ResultState.Error -> ErrorView()
            is ResultState.Success -> {
                HomeContent(
                    listplayer = resultState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    query = homeUiState.query,
                    onQueryChange = viewModelHome::onQueryChange,
                    onUpdateFavoritePlayer = viewModelHome::updateFavoriteplayer
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    listplayer: List<PlayerEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    query: String,
    onQueryChange: (String) -> Unit,
    onUpdateFavoritePlayer: (id: Int, isFavorite: Boolean) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        when (listplayer.isEmpty()) {
            true -> EmptyView()
            false -> AvailableView(listplayer, navController, scaffoldState, onUpdateFavoritePlayer)

        }
    }
}




