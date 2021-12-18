package com.alex.comicdiscovery.feature.character.starred

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.character.starred.model.SideEffect
import com.alex.comicdiscovery.feature.character.starred.model.State
import com.alex.comicdiscovery.ui.components.CharacterItem
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.util.getColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CharacterStarredScreen(navigateToStarlistSettingsScreen: () -> Unit, navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    SideEffects(navigateToStarlistSettingsScreen, navigateToCharacterDetailScreen)

    Column(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {

        Starlist()

        Box(modifier = Modifier.fillMaxSize()) {
            when (val state = viewModel.state.content) {
                is State.Content.Characters -> CharactersScreen(state)
                is State.Content.Message -> MessageScreen(state.message)
            }
        }
    }
}

// ----------------------------------------------------------------------------

@Composable
fun SideEffects(navigateToStarlistSettingsScreen: () -> Unit, navigateToCharacterDetailScreen: (Int) -> Unit) {
    val viewModel: CharacterStarredViewModel = getViewModel()

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.init()

        scope.launch {
            viewModel.event.collect { event ->
                when (event) {
                    is SideEffect.StarlistSettingsScreen -> navigateToStarlistSettingsScreen()
                    is SideEffect.CharacterDetailScreen -> navigateToCharacterDetailScreen(event.id)
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------

@Composable
fun Starlist() {
    val viewModel: CharacterStarredViewModel = getViewModel()

    var expanded by remember { mutableStateOf(false)}

    Column {
        Row {
            when (val starlists = viewModel.state.starlists) {
                is State.Starlist.NoListsAvailable -> {
                    Text(
                        text = stringResource(id = R.string.character_starred_no_starlists_available),
                        modifier = Modifier.weight(1f).padding(16.dp),
                        fontStyle = FontStyle.Italic,
                        color = getColor(DarkCharcoal, BrightGray))
                }
                is State.Starlist.Starlists -> {
                    Row(modifier = Modifier.weight(1f).clickable { expanded = true }.padding(16.dp)) {
                        Text(text = starlists.starlists[viewModel.state.selectedStarlistIndex].name, color = getColor(DarkCharcoal, BrightGray))
                        Icon(Icons.Rounded.ArrowDropDown, contentDescription = null, tint = getColor(UltramarineBlue, BrightGray))
                    }
                }
            }

            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = null,
                modifier = Modifier
                    .clickable { viewModel.onClickStarlistSettings() }
                    .padding(16.dp),
                tint = getColor(UltramarineBlue, BrightGray))
        }

        if (viewModel.state.starlists is State.Starlist.Starlists) {
            val starlists = (viewModel.state.starlists as State.Starlist.Starlists).starlists

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()) {

                starlists.forEachIndexed() { index, starlist ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        viewModel.onClickStarlist(index)
                    }) {
                        Text(text = starlist.name, color = getColor(DarkCharcoal, BrightGray))
                    }
                }
            }
        }

        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(getColor(DarkElectricBlue, BrightGray)))
    }
}

// ----------------------------------------------------------------------------

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun BoxScope.CharactersScreen(state: State.Content.Characters) {
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