package com.alex.features.feature.starlist

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.features.R
import com.alex.features.feature.starlist.model.State
import com.alex.features.ui.theme.*
import com.alex.features.util.getColor
import com.alex.features.util.getTextFieldColors
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun StarlistSettingsScreen() {
    val viewModel: StarlistViewModel = getViewModel()

    Box(
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize()
    ) {

        var showDialogUpdate by remember { mutableStateOf(false) }
        var updateStarlist: State.StarlistItem.Existing? by remember { mutableStateOf(null) }

        var showDialogDelete by remember { mutableStateOf(false) }
        var deleteStarlist: State.StarlistItem.Existing? by remember { mutableStateOf(null) }

        Column {
            Text(
                text = stringResource(R.string.starlist_settings_title_starlists),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            val listState = rememberLazyListState()
            LazyColumn(state = listState) {
                items(items = viewModel.state.starlists) { starlist ->
                    when (starlist) {
                        is State.StarlistItem.Existing -> {
                            StarlistItemExisting(
                                starlist,
                                onClickUpdate = {
                                    showDialogUpdate = true
                                    updateStarlist = it
                                },
                                onClickDelete = {
                                    showDialogDelete = true
                                    deleteStarlist = it
                                }
                            )
                        }
                        State.StarlistItem.New -> {
                            StarlistItemNew()
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = getColor(DarkElectricBlue, BrightGray)
                    )
                }
            }
        }

        if (showDialogUpdate) {
            var starlistUpdateName by remember { mutableStateOf(updateStarlist!!.name) }

            AlertDialog(
                onDismissRequest = { showDialogUpdate = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.updateStarlist(updateStarlist!!.id, starlistUpdateName)
                            showDialogUpdate = false
                        },
                        enabled = starlistUpdateName.isNotBlank()) {
                        Text(text = stringResource(id = R.string.general_update))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialogUpdate = false }) {
                        Text(text = stringResource(id = R.string.general_cancel))
                    }
                },
                text = {
                    TextField(
                        placeholder = { Text(text = stringResource(id = R.string.starlist_settings_placeholder_new)) },
                        value = starlistUpdateName,
                        onValueChange = { starlistUpdateName = it },
                        colors = getTextFieldColors()
                    )
                })
        }

        if (showDialogDelete) {
            AlertDialog(
                onDismissRequest = { showDialogDelete = false },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.onDeleteStarlist(deleteStarlist!!.id)
                        showDialogDelete = false
                    }) {
                        Text(text = stringResource(id = R.string.general_delete))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialogDelete = false }) {
                        Text(text = stringResource(id = R.string.general_cancel))
                    }
                },
                text = {
                    Text(
                        text = stringResource(
                            R.string.starlist_settings_dialog_delete_text,
                            deleteStarlist!!.name
                        )
                    )
                })
        }
    }
}

@Composable
fun StarlistItemExisting(
    starlist: State.StarlistItem.Existing,
    onClickUpdate: (State.StarlistItem.Existing) -> Unit,
    onClickDelete: (State.StarlistItem.Existing) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = starlist.name,
            modifier = Modifier.weight(1f),
            color = getColor(DarkCharcoal, BrightGray)
        )
        IconButton(onClick = { onClickUpdate(starlist) }) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                tint = UltramarineBlue
            )
        }
        IconButton(onClick = { onClickDelete(starlist) }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                tint = CoralRed
            )
        }
    }
}

@Composable
@Preview
fun StarlistItemNew() {
    val viewModel: StarlistViewModel = getViewModel()

    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            placeholder = { Text(text = stringResource(id = R.string.starlist_settings_placeholder_new)) },
            value = viewModel.state.starlistNameNew,
            onValueChange = { viewModel.setNewStarlistName(it) },
            modifier = Modifier.weight(1f),
            colors = getTextFieldColors()
        )

        IconButton(
            onClick = { viewModel.onCreateNewStarlist() },
            enabled = viewModel.state.isStarlistCreateButtonEnabled
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                tint = if (viewModel.state.isStarlistCreateButtonEnabled) UltramarineBlue else DarkElectricBlue
            )
        }
    }
}