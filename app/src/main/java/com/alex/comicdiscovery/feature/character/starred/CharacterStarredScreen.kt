package com.alex.comicdiscovery.feature.character.starred

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.character.starred.model.UiStateContent
import com.alex.comicdiscovery.feature.character.starred.model.UiEventCharacterStarred
import com.alex.comicdiscovery.ui.components.CharacterItem
import com.alex.comicdiscovery.ui.theme.BrightGray
import com.alex.comicdiscovery.ui.theme.DarkCharcoal
import com.alex.comicdiscovery.util.getColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalCoilApi
@Composable
fun CharacterStarredScreen(navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    SideEffects(navigateToCharacterDetailScreen)

    Column(modifier = Modifier
        .background(getColor(lightColor = BrightGray, darkColor = DarkCharcoal))
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
        scope.launch {
            viewModel
                .event
                .map { it as UiEventCharacterStarred.DetailScreen }
                .collect { navigateToCharacterDetailScreen(it.id) }
        }
    }
}

// ----------------------------------------------------------------------------

@ExperimentalCoilApi
@Composable
fun CharactersScreen(state: UiStateContent.Characters) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    LazyColumn {
        items(state.characters) { character ->
            CharacterItem(character) {
                viewModel.onClickCharacter(character.id)
            }
        }
    }
}

// ----------------------------------------------------------------------------

@Composable
fun MessageScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center),
            color = getColor(lightColor = DarkCharcoal, darkColor = BrightGray))
    }
}