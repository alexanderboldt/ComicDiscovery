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
import com.alex.comicdiscovery.feature.character.starred.model.ListState
import com.alex.comicdiscovery.ui.components.CharacterItem
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalCoilApi
@Composable
fun CharacterStarredScreen(navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.detailScreen) {
        scope.launch {
            viewModel.detailScreen.collect { id ->
                navigateToCharacterDetailScreen(id)
            }
        }
    }

    Column(modifier = Modifier.background(AlmostWhite).fillMaxSize()) {
        when (val state = viewModel.listState) {
            is ListState.CharacterState -> {
                LazyColumn {
                    items(state.characters) { character ->
                        CharacterItem(character) {
                            viewModel.onClickCharacter(character.id)
                        }
                    }
                }
            }
            is ListState.MessageState -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = state.message, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}