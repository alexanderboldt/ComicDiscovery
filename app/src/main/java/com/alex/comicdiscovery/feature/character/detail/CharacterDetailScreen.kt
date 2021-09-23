package com.alex.comicdiscovery.feature.character.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.feature.character.detail.model.ContentState
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoilApi
@Composable
fun CharacterDetailScreen(id: Int) {
    val viewModel: CharacterDetailViewModel = getViewModel(parameters = { parametersOf(id, false) })

    when (val state = viewModel.contentState) {
        is ContentState.CharacterState -> {
            Column(modifier = Modifier
                .background(AlmostWhite)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                Image(
                    painter = rememberImagePainter(state.character.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Image(
                    painterResource(id = viewModel.starState),
                    null,
                    Modifier.size(75.dp).clickable { viewModel.onClickStar() })

                Text(text = state.character.name)
                Text(text = state.character.realName ?: "")
                Text(text = state.character.birth)
                Text(text = state.character.aliases)
                Text(text = state.character.gender)
                Text(text = state.character.origin)
                Text(text = state.character.powers)
            }
        }
        is ContentState.LoadingState -> {
            Column(
                modifier = Modifier
                    .background(AlmostWhite)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.message, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        is ContentState.MessageState -> {
            Box(modifier = Modifier
                .background(AlmostWhite)
                .fillMaxSize()) {
                Text(text = state.message, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}