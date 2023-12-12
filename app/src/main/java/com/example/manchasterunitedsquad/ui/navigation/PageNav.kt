package com.example.manchasterunitedsquad.ui.navigation

sealed class PageNav(val route: String) {
    object Home : PageNav("home")
    object Favorite : PageNav("favorite")
    object Profile : PageNav("profile")
    object Detail : PageNav("home/{playerId}") {
        fun createRoute(playerId: Int) = "home/$playerId"
    }
}