package com.alex.features.feature.character.overview

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
import com.alex.features.R
import com.alex.features.feature.character.overview.model.SideEffect
import com.alex.features.feature.character.overview.model.State
import com.alex.features.feature.destinations.CharacterDetailScreenDestination
import com.alex.features.ui.components.*
import com.alex.features.ui.theme.*
import com.alex.features.util.getColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Destination(start = true, route = "CharacterOverviewScreen")
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun CharacterOverviewScreen(navigator: DestinationsNavigator) {
    val viewModel: CharacterOverviewViewModel = getViewModel()

    SideEffects(navigator)

    Column(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {

        Searchbar()

        when (val state = viewModel.state.content) {
            is State.Content.Items -> CharactersScreen(state)
            is State.Content.Loading -> LoadingScreen(state.message)
            is State.Content.Message -> MessageScreen(state.message)
        }
    }
}

// ----------------------------------------------------------------------------

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun SideEffects(navigator: DestinationsNavigator) {
    val viewModel: CharacterOverviewViewModel = getViewModel()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel
                .event
                .map { it as SideEffect.DetailScreen }
                .collect { navigator.navigate(CharacterDetailScreenDestination(it.id, false)) }
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
        value = viewModel.state.query,
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

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CharactersScreen(state: State.Content.Items) {
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