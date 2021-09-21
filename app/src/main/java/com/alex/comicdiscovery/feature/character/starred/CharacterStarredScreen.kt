package com.alex.comicdiscovery.feature.character.starred

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.feature.character.starred.model.ListState
import com.alex.comicdiscovery.feature.character.starred.model.UiModelCharacter
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import org.koin.androidx.compose.getViewModel

@Composable
fun CharacterStarredScreen(viewModel: CharacterStarredViewModel = getViewModel()) {
    Column(modifier = Modifier.background(AlmostWhite).fillMaxSize()) {
        when (val state = viewModel.listState) {
            is ListState.CharacterState -> {
                LazyColumn {
                    items(state.characters) { CharacterItem(it) }
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

@ExperimentalCoilApi
@Composable
fun CharacterItem(character: UiModelCharacter) {
    Column(modifier = Modifier
        .background(AlmostWhite)
        .padding(16.dp)) {
        Image(
            painter = rememberImagePainter(character.iconUrl),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth())
        Text(text = character.name)
        Text(text = character.realName.orEmpty())
    }
}