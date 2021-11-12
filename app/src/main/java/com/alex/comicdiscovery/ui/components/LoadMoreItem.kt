package com.alex.comicdiscovery.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.R

@Composable
fun LoadMoreItem(isEnabled: Boolean, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        ComicDiscoveryOutlinedButton(
            text = stringResource(id = R.string.character_overview_load_more_characters),
            isEnabled = isEnabled,
            onClick = onClick)
    }
}

data class UiModelLoadMore(var isEnabled: Boolean) : UiModelBaseList