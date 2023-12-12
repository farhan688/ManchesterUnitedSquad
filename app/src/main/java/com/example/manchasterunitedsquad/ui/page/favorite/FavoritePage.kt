package com.example.manchasterunitedsquad.ui.page.favorite

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.manchasterunitedsquad.data.PlayerEntity
import com.example.manchasterunitedsquad.model.ViewModelFavorite
import com.example.manchasterunitedsquad.ui.component.AvailableView
import com.example.manchasterunitedsquad.ui.component.EmptyView
import com.example.manchasterunitedsquad.ui.component.ErrorView
import com.example.manchasterunitedsquad.ui.component.Loader
import com.example.manchasterunitedsquad.utils.ResultState

@Composable
fun FavoritePage(navController: NavController, scaffoldState: ScaffoldState) {
    val viewModelFavorite = hiltViewModel<ViewModelFavorite>()

    viewModelFavorite.allFavoritePlayer.collectAsState(ResultState.Loading).value.let { resultState ->
        when (resultState) {
            is ResultState.Loading -> Loader()
            is ResultState.Error -> ErrorView()
            is ResultState.Success -> {
                FavoriteContent(
                    listFavoriteplayer = resultState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateFavoritePlayer = viewModelFavorite::updateFavoritePlayer
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listFavoriteplayer: List<PlayerEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlayer: (id: Int, isFavorite: Boolean) -> Unit
) {
    when (listFavoriteplayer.isEmpty()) {
        true -> EmptyView()
        false -> AvailableView(listFavoriteplayer, navController, scaffoldState, onUpdateFavoritePlayer)
    }
}