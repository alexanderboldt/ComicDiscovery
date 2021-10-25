package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.feature.character.overview.model.UiStateContent
import com.alex.comicdiscovery.feature.character.overview.model.UiEventCharacterOverview
import com.alex.comicdiscovery.ui.components.CharacterItem
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun CharacterOverviewScreen(navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterOverviewViewModel = getViewModel()

    SideEffects(navigateToCharacterDetailScreen)

    Column(modifier = Modifier
        .background(AlmostWhite)
        .fillMaxSize()) {

        Searchbar()

        when (val state = viewModel.listState) {
            is UiStateContent.Characters -> CharactersScreen(state)
            is UiStateContent.Loading -> LoadingScreen(state.message)
            is UiStateContent.Message -> MessageScreen(state.message)
        }
    }
}

// ----------------------------------------------------------------------------

@Composable
fun SideEffects(navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterOverviewViewModel = getViewModel()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel
                .event
                .map { it as UiEventCharacterOverview.DetailScreen }
                .collect { navigateToCharacterDetailScreen(it.id) }
        }
    }
}

// ----------------------------------------------------------------------------

@ExperimentalComposeUiApi
@Composable
fun Searchbar() {
    val viewModel: CharacterOverviewViewModel = getViewModel()
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

// ----------------------------------------------------------------------------

@ExperimentalCoilApi
@Composable
fun CharactersScreen(state: UiStateContent.Characters) {
    val viewModel: CharacterOverviewViewModel = getViewModel()

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
fun LoadingScreen(message: String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

// ----------------------------------------------------------------------------

@Composable
fun MessageScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = message, modifier = Modifier.align(Alignment.Center))
    }
}