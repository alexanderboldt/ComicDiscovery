package com.alex.features.feature.character.detail

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alex.features.R
import com.alex.features.feature.character.detail.model.State
import com.alex.features.feature.character.overview.LoadingScreen
import com.alex.features.feature.destinations.ImageScreenDestination
import com.alex.features.ui.theme.*
import com.alex.features.ui.theme.UltramarineBlue
import com.alex.features.util.getColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Destination
@Composable
fun CharacterDetailScreen(id: Int, userComesFromStarredScreen: Boolean, navigator: DestinationsNavigator) {
    val viewModel: CharacterDetailViewModel = getViewModel { parametersOf(id, userComesFromStarredScreen) }

    when (val state = viewModel.state.content) {
        is State.Content.Character -> CharacterScreen(state, viewModel, navigator)
        is State.Content.Loading -> LoadingScreen(state.message)
        is State.Content.Message -> MessageScreen(state.message)
    }
}

// ----------------------------------------------------------------------------

@Composable
fun CharacterScreen(
    state: State.Content.Character,
    viewModel: CharacterDetailViewModel,
    navigator: DestinationsNavigator
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = state.character.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable { navigator.navigate(ImageScreenDestination(state.character.imageUrl)) },
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(16.dp)) {
                AttributeItem(R.string.character_detail_name, state.character.name)
                AttributeItem(R.string.character_detail_summary, state.character.summary)
                AttributeItem(R.string.character_detail_real_name, state.character.realName)
                AttributeItem(R.string.character_detail_aliases, state.character.aliases)
                AttributeItem(R.string.character_detail_gender, state.character.gender)
                AttributeItem(R.string.character_detail_birth, state.character.birth)
                AttributeItem(R.string.character_detail_origin, state.character.origin)
                AttributeItem(R.string.character_detail_powers, state.character.powers)
                AttributeItem(R.string.character_detail_teams, state.character.teams)
                AttributeItem(R.string.character_detail_friends, state.character.friends)
                AttributeItem(R.string.character_detail_enemies, state.character.enemies)
            }
        }

        Starlist(viewModel)
    }
}

@Composable
fun AttributeItem(@StringRes label: Int, text: String) {
    Row(Modifier.padding(bottom = 8.dp)) {
        Text(
            text = stringResource(label),
            modifier = Modifier.width(100.dp),
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.body1
        )
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun BoxWithConstraintsScope.Starlist(viewModel: CharacterDetailViewModel) {
    var isExpanded by remember { mutableStateOf(false) }

    val animateWidth by animateDpAsState(if (isExpanded) this.maxWidth else 64.dp)
    val animateHeight by animateDpAsState(if (isExpanded) 200.dp else 64.dp)
    val animateCorner by animateIntAsState(if (isExpanded) 5 else 50)

    Surface(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .size(animateWidth, animateHeight),
        shape = RoundedCornerShape(animateCorner),
        color = CoralRed,
        elevation = 6.dp
    ) {

        Box {
            AnimatedVisibility(visible = isExpanded, enter = fadeIn(), exit = fadeOut()) {
                when (val starlists = viewModel.state.starlist) {
                    is State.Starlist.NoListsAvailable -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(id = R.string.character_detail_no_starlists))
                        }
                    }
                    is State.Starlist.Starlists -> {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            LazyColumn {
                                items(items = starlists.starlists) { starlist ->
                                    StarlistItem(starlist, viewModel)
                                }
                            }
                        }
                    }
                }
            }

            if (viewModel.state.isStarlistLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = { },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                        .background(DarkCharcoal.copy(alpha = 0.3f))
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = BrightGray
                    )
                }
            }

            Icon(
                imageVector = if (isExpanded) Icons.Rounded.Check else Icons.Rounded.Star,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .clickable { isExpanded = !isExpanded }
                    .size(64.dp)
                    .padding(16.dp),
                tint = BrightGray
            )
        }
    }
}

@Composable
fun StarlistItem(starlist: State.UiModelStarlist, viewModel: CharacterDetailViewModel) {
    Row(modifier = Modifier
        .clickable { viewModel.onClickCheckStarlist(starlist.id, !starlist.isChecked) }
        .fillMaxWidth()
        .padding(16.dp)) {
        Checkbox(
            checked = starlist.isChecked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = BrightGray,
                uncheckedColor = BrightGray,
                checkmarkColor = CoralRed
            )
        )
        Spacer(Modifier.width(MaterialTheme.spacing.medium))
        Text(text = starlist.name, color = BrightGray)
    }
}

// ----------------------------------------------------------------------------

@Composable
fun LoadingScreen(message: String) {
    Column(
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = getColor(UltramarineBlue, BrightGray)
        )

        Spacer(Modifier.height(MaterialTheme.spacing.medium))

        Text(
            text = message,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = getColor(DarkCharcoal, BrightGray)
        )
    }
}

// ----------------------------------------------------------------------------

@Composable
fun MessageScreen(message: String) {
    Box(
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = getColor(DarkCharcoal, BrightGray)
        )
    }
}