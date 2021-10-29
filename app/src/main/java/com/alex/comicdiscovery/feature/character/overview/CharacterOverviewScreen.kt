package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.character.overview.model.UiStateContent
import com.alex.comicdiscovery.feature.character.overview.model.UiEventCharacterOverview
import com.alex.comicdiscovery.ui.components.CharacterItem
import com.alex.comicdiscovery.ui.theme.BrightGray
import com.alex.comicdiscovery.ui.theme.DarkCharcoal
import com.alex.comicdiscovery.ui.theme.UltramarineBlue
import com.alex.comicdiscovery.util.getColor
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
        .background(getColor(BrightGray, DarkCharcoal))
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

    val colors = if (MaterialTheme.colors.isLight) {
        TextFieldDefaults.textFieldColors(
            backgroundColor = BrightGray,
            leadingIconColor = UltramarineBlue,
            textColor = DarkCharcoal,
            focusedIndicatorColor = UltramarineBlue,
            focusedLabelColor = UltramarineBlue,
            cursorColor = DarkCharcoal
        )
    } else {
        TextFieldDefaults.textFieldColors(
            backgroundColor = DarkCharcoal,
            leadingIconColor = BrightGray,
            textColor = BrightGray,
            focusedIndicatorColor = BrightGray,
            focusedLabelColor = BrightGray,
            cursorColor = BrightGray
        )
    }

    TextField(
        value = viewModel.query,
        onValueChange = { value -> viewModel.onQueryChange(value) },
        label = { Text(stringResource(id = R.string.character_overview_search_hint)) },
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
        ),
        shape = RoundedCornerShape(0.dp),
        colors = colors
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
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = getColor(UltramarineBlue, BrightGray))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = getColor(DarkCharcoal, BrightGray))
    }
}

// ----------------------------------------------------------------------------

@Composable
fun MessageScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center),
            color = getColor(DarkCharcoal, BrightGray))
    }
}