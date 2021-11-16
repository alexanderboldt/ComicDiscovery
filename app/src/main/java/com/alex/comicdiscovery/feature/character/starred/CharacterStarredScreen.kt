package com.alex.comicdiscovery.feature.character.starred

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.character.starred.model.UiStateContent
import com.alex.comicdiscovery.feature.character.starred.model.UiEventCharacterStarred
import com.alex.comicdiscovery.ui.components.CharacterItem
import com.alex.comicdiscovery.ui.theme.BrightGray
import com.alex.comicdiscovery.ui.theme.CoralRed
import com.alex.comicdiscovery.ui.theme.DarkCharcoal
import com.alex.comicdiscovery.util.getColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CharacterStarredScreen(navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    SideEffects(navigateToCharacterDetailScreen)

    Box(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {
        when (val state = viewModel.content) {
            is UiStateContent.Characters -> CharactersScreen(state)
            is UiStateContent.Message -> MessageScreen(state.message)
        }
    }
}

// ----------------------------------------------------------------------------

@Composable
fun SideEffects(navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.init()

        scope.launch {
            viewModel
                .event
                .map { it as UiEventCharacterStarred.DetailScreen }
                .collect { navigateToCharacterDetailScreen(it.id) }
        }
    }
}

// ----------------------------------------------------------------------------

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun BoxScope.CharactersScreen(state: UiStateContent.Characters) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        items(
            items = state.characters,
            key = { character -> character.id }) { character ->
            CharacterItem(character) {
                viewModel.onClickCharacter(character.id)
            }
        }
    }

    AnimatedVisibility(
        visible = listState.firstVisibleItemIndex >= 1,
        modifier = Modifier.align(Alignment.BottomEnd),
        enter = expandIn(animationSpec = tween(300), expandFrom = Alignment.TopStart),
        exit = shrinkOut(animationSpec = tween(300), shrinkTowards = Alignment.TopStart)
    ) {

        FloatingActionButton(
            onClick = { scope.launch { listState.animateScrollToItem(0) } },
            backgroundColor = CoralRed,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)) {
            Image(
                imageVector = Icons.Rounded.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(BrightGray)
            )
        }
    }
}

// ----------------------------------------------------------------------------

@Composable
fun BoxScope.MessageScreen(message: String) {
    Text(
        text = message,
        modifier = Modifier.align(Alignment.Center),
        color = getColor(DarkCharcoal, BrightGray))
}