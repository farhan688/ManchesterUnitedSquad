package com.example.manchasterunitedsquad.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manchasterunitedsquad.R
import com.example.manchasterunitedsquad.ui.navigation.ItemNav
import com.example.manchasterunitedsquad.ui.navigation.PageNav
import com.example.manchasterunitedsquad.ui.page.about.AboutPage
import com.example.manchasterunitedsquad.ui.page.detail.DetailPage
import com.example.manchasterunitedsquad.ui.page.favorite.FavoritePage
import com.example.manchasterunitedsquad.ui.page.home.HomePage

@Composable
fun MainPage (modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != PageNav.Detail.route) {
                BottomBar(navController, currentRoute)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, shape = RoundedCornerShape(8.dp))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PageNav.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(PageNav.Home.route) {
                HomePage(navController, scaffoldState)
            }
            composable(
                route = PageNav.Detail.route,
                arguments = listOf(
                    navArgument("playerId") { type = NavType.IntType }
                )
            ) {
                val tourismId = it.arguments?.getInt("playerId") ?: 0
                DetailPage(tourismId, navController, scaffoldState)
            }
            composable(PageNav.Favorite.route) {
                FavoritePage(navController, scaffoldState)
            }
            composable(PageNav.Profile.route) {
                AboutPage()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        ItemNav(
            title = stringResource(R.string.home),
            icon = Icons.Rounded.Home,
            page = PageNav.Home
        ),
        ItemNav(
            title = stringResource(R.string.favorite),
            icon = Icons.Rounded.Favorite,
            page = PageNav.Favorite
        ),
        ItemNav(
            title = stringResource(R.string.profile),
            icon = Icons.Rounded.Person,
            page = PageNav.Profile
        ),
    )

    BottomNavigation(backgroundColor = Color.White) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    val contentDescription = when (item.page) {
                        is PageNav.Profile -> "about_page"
                        else -> item.title
                    }
                    Icon(
                        imageVector = item.icon,
                        contentDescription = contentDescription
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.page.route,
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.page.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }

}