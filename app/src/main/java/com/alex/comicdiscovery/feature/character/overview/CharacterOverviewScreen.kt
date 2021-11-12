package com.alex.comicdiscovery.feature.character.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.character.overview.model.UiStateContent
import com.alex.comicdiscovery.feature.character.overview.model.UiEventCharacterOverview
import com.alex.comicdiscovery.ui.components.*
import com.alex.comicdiscovery.ui.theme.*
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

        when (val state = viewModel.content) {
            is UiStateContent.Items -> CharactersScreen(state)
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
            // background
            backgroundColor = BrightGray,
            // icon
            leadingIconColor = UltramarineBlue,
            // label
            unfocusedLabelColor = DarkElectricBlue,
            focusedLabelColor = UltramarineBlue,
            // text
            textColor = DarkCharcoal,
            // indicator
            unfocusedIndicatorColor = DarkElectricBlue,
            focusedIndicatorColor = UltramarineBlue,
            // cursor
            cursorColor = DarkElectricBlue
        )
    } else {
        TextFieldDefaults.textFieldColors(
            // background
            backgroundColor = DarkCharcoal,
            // icon
            leadingIconColor = BrightGray,
            // label
            unfocusedLabelColor = BrightGray,
            focusedLabelColor = BrightGray,
            // text
            textColor = BrightGray,
            // indicator
            unfocusedIndicatorColor = BrightGray,
            focusedIndicatorColor = BrightGray,
            // cursor
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
fun CharactersScreen(state: UiStateContent.Items) {
    Box(modifier = Modifier.fillMaxSize()) {
        val viewModel: CharacterOverviewViewModel = getViewModel()

        val listState = rememberLazyListState()

        val scope = rememberCoroutineScope()

        LazyColumn(state = listState) {
            items(
                items = state.items,
                key = { it.hashCode() }) { item ->
                when (item) {
                    is UiModelCharacter -> CharacterItem(item) { viewModel.onClickCharacter(item.id) }
                    is UiModelLoadMore -> LoadMoreItem(item.isEnabled) { viewModel.onClickLoadMore() }
                }
            }
        }

        if (listState.firstVisibleItemIndex >= 1) {
            FloatingActionButton(
                onClick = { scope.launch { listState.animateScrollToItem(0) } },
                backgroundColor = CoralRed,
                modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd)) {
                Image(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(BrightGray)
                )
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