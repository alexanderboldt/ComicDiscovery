package com.alex.comicdiscovery.feature.starlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.feature.starlist.model.UiModelStarlistItem
import com.alex.comicdiscovery.ui.components.ComicDiscoveryButton
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.util.getColor
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
fun StarlistSettingsScreen() {
    val viewModel: StarlistViewModel = getViewModel()

    Box(modifier = Modifier
        .background(getColor(BrightGray, DarkCharcoal))
        .fillMaxSize()) {

        var showDialogDelete by remember { mutableStateOf(false) }
        var deleteStarlist: UiModelStarlistItem.Existing? by remember { mutableStateOf(null) }

        Column {
            val textStyle = TextStyle(
                color = getColor(DarkElectricBlue, BrightGray),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium)

            Text(
                text = stringResource(R.string.starlist_settings_title_starlists),
                style = textStyle,
                modifier = Modifier.padding(16.dp))

            val listState = rememberLazyListState()
            LazyColumn(state = listState) {
                items(items = viewModel.starlists) { starlist ->
                    when (starlist) {
                        is UiModelStarlistItem.Existing -> {
                            StarlistItemExisting(starlist) {
                                showDialogDelete = true
                                deleteStarlist = it
                            }
                        }
                        UiModelStarlistItem.New -> {
                            StarlistItemNew()
                        }
                    }

                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(getColor(DarkElectricBlue, BrightGray)))
                }
            }
        }

        if (showDialogDelete) {
            AlertDialog(
                onDismissRequest = { showDialogDelete = false },
                confirmButton = {
                    ComicDiscoveryButton(text = stringResource(id = R.string.general_delete)) {
                        viewModel.onDeleteStarlist(deleteStarlist!!.id)
                        showDialogDelete = false
                    }
                },
                dismissButton = {
                    ComicDiscoveryButton(text = stringResource(id = R.string.general_cancel)) {
                        showDialogDelete = false
                    }
                },
                text = {
                    Text(text = stringResource(R.string.starlist_settings_dialog_delete_text, deleteStarlist!!.name))
                })
        }
    }
}

@Composable
fun StarlistItemExisting(starlist: UiModelStarlistItem.Existing, onClickDelete: (UiModelStarlistItem.Existing) -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = starlist.name, modifier = Modifier.weight(1f), color = getColor(DarkCharcoal, BrightGray))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                tint = UltramarineBlue)
        }
        IconButton(onClick = { onClickDelete(starlist) }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                tint = CoralRed)
        }
    }
}

@Composable
@Preview
fun StarlistItemNew() {
    val viewModel: StarlistViewModel = getViewModel()

    // todo: centralize
    val colors = if (MaterialTheme.colors.isLight) {
        TextFieldDefaults.textFieldColors(
            // background
            backgroundColor = BrightGray,
            // icon
            leadingIconColor = UltramarineBlue,
            // label
            unfocusedLabelColor = DarkElectricBlue,
            focusedLabelColor = UltramarineBlue,
            // text
            textColor = DarkCharcoal,
            // indicator
            unfocusedIndicatorColor = DarkElectricBlue,
            focusedIndicatorColor = UltramarineBlue,
            // cursor
            cursorColor = DarkElectricBlue
        )
    } else {
        TextFieldDefaults.textFieldColors(
            // background
            backgroundColor = DarkCharcoal,
            // icon
            leadingIconColor = BrightGray,
            // label
            unfocusedLabelColor = BrightGray,
            focusedLabelColor = BrightGray,
            // text
            textColor = BrightGray,
            // indicator
            unfocusedIndicatorColor = BrightGray,
            focusedIndicatorColor = BrightGray,
            // cursor
            cursorColor = BrightGray
        )
    }

    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        TextField(
            placeholder = { Text(text = stringResource(id = R.string.starlist_settings_placeholder_new))},
            value = viewModel.starlistNameNew,
            onValueChange = { viewModel.setNewStarlistName(it) },
            modifier = Modifier.weight(1f),
            colors = colors)

        IconButton(onClick = { viewModel.onCreateNewStarlist() }, enabled = viewModel.isStarlistCreateButtonEnabled) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = if (viewModel.isStarlistCreateButtonEnabled) UltramarineBlue else DarkElectricBlue)
        }
    }
}