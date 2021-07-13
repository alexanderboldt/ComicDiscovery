package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.feature.character.overview.model.UiModelCharacter
import com.alex.comicdiscovery.feature.character.overview.model.ListState
import com.alex.comicdiscovery.ui.theme.AlmostBlack
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import com.alex.comicdiscovery.ui.theme.Blue300
import com.google.accompanist.coil.rememberCoilPainter
import org.koin.androidx.compose.getViewModel

@ExperimentalComposeUiApi
@Composable
fun CharacterOverviewScreen(viewModel: CharacterOverviewViewModel = getViewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        TextField(
            value = viewModel.query,
            onValueChange = { value -> viewModel.onQueryChange(value) },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Filled.Search, null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.onQuerySubmit()
                keyboardController?.hide() })
        )

        when (val state = viewModel.listState) {
            is ListState.CharacterState -> {
                LazyColumn {
                    items(state.characters) { CharacterItem(it) }
                }
            }
            is ListState.MessageState -> Text(text = state.message, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun CharacterItem(character: UiModelCharacter) {
    Column(modifier = Modifier
        .background(AlmostWhite)
        .padding(16.dp)) {
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