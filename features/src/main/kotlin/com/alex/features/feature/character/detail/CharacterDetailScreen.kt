package com.alex.features.feature.character.detail

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.features.R
import com.alex.features.feature.character.detail.model.*
import com.alex.features.feature.character.detail.model.State
import com.alex.features.ui.theme.*
import com.alex.features.ui.theme.UltramarineBlue
import com.alex.features.util.getColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun CharacterDetailScreen(id: Int, userComesFromStarredScreen: Boolean, navigateToImageScreen: (String) -> Unit) {
    val viewModel: CharacterDetailViewModel = getViewModel(parameters = { parametersOf(id, userComesFromStarredScreen) })

    SideEffects(viewModel)

    when (val state = viewModel.state.content) {
        is State.Content.Character -> CharacterScreen(state, viewModel, navigateToImageScreen)
        is State.Content.Loading -> LoadingScreen(state.message)
        is State.Content.Message -> MessageScreen(state.message)
    }
}

// ----------------------------------------------------------------------------

@Composable
fun SideEffects(viewModel: CharacterDetailViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel
                .event
                .map { it as SideEffect.Message }
                .collect { Toast.makeText(context, it.message, Toast.LENGTH_LONG).show() }
        }
    }
}

// ----------------------------------------------------------------------------

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun CharacterScreen(state: State.Content.Character, viewModel: CharacterDetailViewModel, navigateToImageScreen: (String) -> Unit) {
    BoxWithConstraints(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {

            Image(
                painter = rememberImagePainter(state.character.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable { navigateToImageScreen(state.character.imageUrl) },
                contentScale = ContentScale.Crop
            )

            ProvideTextStyle(value = if (MaterialTheme.colors.isLight) TextStyle(DarkCharcoal) else TextStyle(BrightGray)) {
                Column(Modifier.padding(16.dp)) {
                    AttributeItem(R.string.character_detail_name, state.character.name)
                    AttributeItem(R.string.character_detail_real_name, state.character.realName)
                    AttributeItem(R.string.character_detail_aliases, state.character.aliases)
                    AttributeItem(R.string.character_detail_gender, state.character.gender)
                    AttributeItem(R.string.character_detail_birth, state.character.birth)
                    AttributeItem(R.string.character_detail_origin, state.character.origin)
                    AttributeItem(R.string.character_detail_powers, state.character.powers)
                }
            }
        }

        Starlist(viewModel)
    }
}

@Composable
fun AttributeItem(@StringRes label: Int, text: String) {
    Row(Modifier.padding(bottom = 8.dp)) {
        Text(text = stringResource(label), modifier = Modifier.width(100.dp), fontWeight = FontWeight.Medium)
        Text(text = text)
    }
}

@ExperimentalAnimationApi
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
        elevation = 6.dp) {

        Box {
            AnimatedVisibility(visible = isExpanded, enter = fadeIn(), exit = fadeOut()) {
                when (val starlists = viewModel.state.starlist) {
                    is State.Starlist.NoListsAvailable -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.character_detail_no_starlists),
                                modifier = Modifier.align(Alignment.Center)
                            )
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
            ))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = starlist.name, color = BrightGray)
    }
}

// ----------------------------------------------------------------------------

@Composable
fun LoadingScreen(message: String) {
    Column (
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center) {
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
    Box(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center),
            color = getColor(DarkCharcoal, BrightGray))
    }
}