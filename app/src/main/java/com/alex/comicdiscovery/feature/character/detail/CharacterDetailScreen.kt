package com.alex.comicdiscovery.feature.character.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.feature.character.detail.models.ContentState
import com.alex.comicdiscovery.ui.theme.AlmostWhite
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalCoilApi
@Composable
fun CharacterDetailScreen(id: Int, viewModel: CharacterDetailViewModel = getViewModel(parameters = { parametersOf(id, false) })) {
    when (val state = viewModel.contentState) {
        is ContentState.CharacterState -> {
            Column(modifier = Modifier
                .background(AlmostWhite)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                Image(
                    painter = rememberImagePainter(state.character.imageUrl),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth
                )

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
                modifier = Modifier.background(AlmostWhite).fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.message, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        is ContentState.MessageState -> {
            Box(modifier = Modifier.background(AlmostWhite).fillMaxSize()) {
                Text(text = state.message, modifier = Modifier.align(Alignment.Center))
            }
        }
    }

    /*
    private fun setupViewBinding() {
        lifecycleScope.launch {
            binding.imageViewStar.clicks {
                viewModel.onClickStar()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.contentState.observe { state ->
            when (state) {
                is ContentState.CharacterState -> {
                    binding.apply {
                        viewSwitcher.displayedChild = 0

                        imageViewAvatar.load(state.character.imageUrl) {
                            crossfade(500)
                        }
                        textViewName.text = state.character.name
                        textViewRealName.text = state.character.realName
                        textViewAliases.text = state.character.aliases
                        textViewGender.text = state.character.gender
                        textViewBirth.text = state.character.birth
                        textViewPowers.text = state.character.powers
                        textViewOrigin.text = state.character.origin
                    }
                }
                is ContentState.LoadingState -> {
                    binding.apply {
                        viewSwitcher.displayedChild = 1
                        contentLoadingProgressBar.isVisible = true
                        contentLoadingProgressBar.show()
                        textViewMessage.text = state.message
                    }
                }
                is ContentState.MessageState -> {
                    binding.apply {
                        viewSwitcher.displayedChild = 1
                        contentLoadingProgressBar.isGone = true
                        contentLoadingProgressBar.hide()
                        textViewMessage.text = state.message
                    }
                }
            }
        }

        viewModel.starState.observe { state ->
            binding.imageViewStar.setImageResource(state)
        }
    }
     */
}