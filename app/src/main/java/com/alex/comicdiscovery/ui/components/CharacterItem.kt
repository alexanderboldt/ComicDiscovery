package com.alex.comicdiscovery.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            .background(AlmostWhite)
            .padding(16.dp)) {
        Image(
            painter = rememberImagePainter(character.iconUrl),
            contentDescription = null,
            modifier = Modifier.height(200.dp).fillMaxWidth()
        )
        Text(text = character.name)
        Text(text = character.realName.orEmpty())
    }
}