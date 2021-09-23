package com.alex.comicdiscovery.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.ui.theme.AlmostWhite

@ExperimentalCoilApi
@Composable
fun CharacterItem(character: UiModelCharacter, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(AlmostWhite)) {
        Image(
            painter = rememberImagePainter(character.iconUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(8.dp, 8.dp, 8.dp, 16.dp)) {
            Text(text = character.name)
            Text(text = character.realName.orEmpty())
        }
    }
}