package com.example.manchasterunitedsquad.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.manchasterunitedsquad.R
import com.example.manchasterunitedsquad.data.PlayerEntity
import com.example.manchasterunitedsquad.ui.navigation.PageNav
import com.example.manchasterunitedsquad.utils.countRate
import com.example.manchasterunitedsquad.utils.formatNumber
import kotlinx.coroutines.launch

@Composable
fun AvailableView(
    listplayer: List<PlayerEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlayers: (id: Int, isFavorite: Boolean) -> Unit,
) {
    LazyColumn {
        items(listplayer, key = { it.name }) { player ->
            PlayerItems(player, navController, scaffoldState, onUpdateFavoritePlayers)
        }
    }
}

@Composable
fun PlayerItems(
    players: PlayerEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlayer: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, _, country, photo, skillMoves, marketValue,  isFavorite) = players

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, Color.LightGray.copy(0.5f), MaterialTheme.shapes.small)
            .clickable { navController.navigate(PageNav.Detail.createRoute(id ?: 0)) },
    ) {
        Column {
            Box {
                AsyncImage(
                    model = photo,
                    contentDescription = name,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.baseline_img),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = country,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6
                    )

                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                        contentDescription = name,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onUpdateFavoritePlayer(id ?: 0, !isFavorite)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val nRate = countRate(skillMoves)
                    repeat(nRate) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = name,
                            tint = Color(0xFFFFCC00)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "skill moves: $skillMoves/5 \nmarket value: Rp.${formatNumber(marketValue)}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}