package com.alex.comicdiscovery.feature.character.detail

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.character.detail.model.UiStateContent
import com.alex.comicdiscovery.feature.character.detail.model.UiEventCharacterDetail
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.ui.theme.UltramarineBlue
import com.alex.comicdiscovery.util.getColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoilApi
@Composable
fun CharacterDetailScreen(id: Int, userComesFromStarredScreen: Boolean, navigateToImageScreen: (String) -> Unit) {
    val viewModel: CharacterDetailViewModel = getViewModel(parameters = { parametersOf(id, userComesFromStarredScreen) })

    SideEffects(viewModel)

    when (val state = viewModel.content) {
        is UiStateContent.Character -> CharacterScreen(state, viewModel, navigateToImageScreen)
        is UiStateContent.Loading -> LoadingScreen(state.message)
        is UiStateContent.Message -> MessageScreen(state.message)
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
                .map { it as UiEventCharacterDetail.Message }
                .collect { Toast.makeText(context, it.message, Toast.LENGTH_LONG).show() }
        }
    }
}

// ----------------------------------------------------------------------------

@ExperimentalCoilApi
@Composable
fun CharacterScreen(state: UiStateContent.Character, viewModel: CharacterDetailViewModel, navigateToImageScreen: (String) -> Unit) {
    Box(modifier = Modifier
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

        FloatingActionButton(
            onClick = { viewModel.onClickStar() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp).border(if (viewModel.isStarred) 4.dp else 0.dp, UltramarineBlue, CircleShape),
            backgroundColor = getColor(CoralRed, DarkElectricBlue)) {
            Image(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(BrightGray))
        }
    }
}

@Composable
fun AttributeItem(@StringRes label: Int, text: String) {
    Row(Modifier.padding(bottom = 8.dp)) {
        Text(text = stringResource(label), modifier = Modifier.width(100.dp), fontWeight = FontWeight.Medium)
        Text(text = text)
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