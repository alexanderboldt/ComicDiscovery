package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.feature.character.overview.model.ListState
import com.alex.comicdiscovery.feature.character.overview.model.UiModelCharacter
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import com.google.accompanist.coil.rememberCoilPainter
import org.koin.androidx.compose.getViewModel

@ExperimentalComposeUiApi
@Composable
fun CharacterOverviewScreen(navigateToCharacterDetailScreen: (Int) -> Unit, viewModel: CharacterOverviewViewModel = getViewModel()) {
    Column(modifier = Modifier.background(AlmostWhite).fillMaxSize()) {
        Searchbar(viewModel)

        when (val state = viewModel.listState) {
            is ListState.CharacterState -> {
                if (viewModel.detailScreen != -1) {
                    navigateToCharacterDetailScreen(viewModel.detailScreen)
                }

                LazyColumn {
                    items(state.characters) { CharacterItem(it, viewModel) }
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

@ExperimentalComposeUiApi
@Composable
fun Searchbar(viewModel: CharacterOverviewViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = viewModel.query,
        onValueChange = { value -> viewModel.onQueryChange(value) },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Filled.Search, null) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                viewModel.onQuerySubmit()
                keyboardController?.hide()
            }
        )
    )
}

@Composable
fun CharacterItem(character: UiModelCharacter, viewModel: CharacterOverviewViewModel) {
    Column(
        modifier = Modifier
            .clickable { viewModel.onClickCharacter(character.id) }
            .padding(16.dp)
            .background(AlmostWhite)) {
        Image(
            painter = rememberCoilPainter(request = character.iconUrl),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth())
        Text(text = character.name)
        Text(text = character.realName.orEmpty())
    }
}