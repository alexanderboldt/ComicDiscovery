package com.alex.features.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alex.features.ui.theme.BrightGray
import com.alex.features.ui.theme.DarkCharcoal

@Composable
fun CharacterItem(character: UiModelCharacter, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(if (MaterialTheme.colors.isLight) BrightGray else DarkCharcoal)) {
        AsyncImage(
            model = character.iconUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(8.dp, 8.dp, 8.dp, 16.dp)) {
            Text(text = character.name, style = MaterialTheme.typography.body1)
            Text(text = character.realName.orEmpty(), style = MaterialTheme.typography.body1)
        }
    }
}

data class UiModelCharacter(
    val id: Int,
    val name: String,
    val realName: String?,
    val iconUrl: String
) : UiModelBaseList