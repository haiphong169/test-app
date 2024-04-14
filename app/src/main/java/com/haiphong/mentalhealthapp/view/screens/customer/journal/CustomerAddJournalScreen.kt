package com.haiphong.mentalhealthapp.view.screens.customer.journal

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.haiphong.mentalhealthapp.viewmodel.journal.CustomerAddJournalViewModel

// todo: 2 mục title và content trống không và một button

enum class ItemType {
    TEXT,
    IMAGE
}

sealed interface BaseItemNote<out T> {
    data class Text<out T>(val content:T, val type:ItemType = ItemType.TEXT):BaseItemNote<T>
    data class Image<out T>(val url:T, val type:ItemType = ItemType.IMAGE):BaseItemNote<T>
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerAddJournalScreen(
    onSave: () -> Unit,
    viewModel: CustomerAddJournalViewModel = viewModel()
) {
    val addJournalState by viewModel.addJournalState.collectAsState()
    val items = remember{mutableStateListOf<BaseItemNote<String>>()}
    var isShowTextEditor by remember { mutableStateOf(false)}
    var currentInputTextEditor by remember { mutableStateOf("")}
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            items.add(BaseItemNote.Image(it.toString()))
        }
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            TextField(
                value = addJournalState.title,
                onValueChange = { viewModel.onTitleChange(it) },
                placeholder = { Text(text = "Untitled") },
                isError = addJournalState.isInvalidated,
                singleLine = true,
                modifier = Modifier
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    placeholderColor = Color.Gray,
                    containerColor = Color(0xFAFAFA)
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                if (addJournalState.title == "" || items.isEmpty()) {
                    viewModel.setErrorMessage("Please enter both title and content.")
                } else {
                    viewModel.addJournal(items,onSave)
                }
            }) {
                Text(text = "Save")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        items.forEach {
            when(it){
                is BaseItemNote.Text -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it.content,
                        textAlign = TextAlign.Start
                    )
                }
                is BaseItemNote.Image -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                            ,
                            contentScale = ContentScale.Fit,
                            model = ImageRequest.Builder(LocalContext.current).data(it.url).crossfade(true).build(),
                            contentDescription = null,
                        )
                        Text(text = it.url,color = Color.LightGray, textAlign = TextAlign.Center)
                    }
                }
            }
        }
        AnimatedVisibility(visible = isShowTextEditor) {
            TextField(
                value = currentInputTextEditor,
                onValueChange = {
                    currentInputTextEditor = it
                },
                placeholder = { Text(text = "What's on your head right now?") },
                isError = addJournalState.isInvalidated,
                colors = TextFieldDefaults.textFieldColors(
                    placeholderColor = Color.Gray,
                    containerColor = Color(0xFAFAFA)
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isShowTextEditor = false
                            items.add(BaseItemNote.Text(currentInputTextEditor))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Send,
                            contentDescription = "Send"
                        )
                    }
                },
                leadingIcon = {
                    IconButton(
                        onClick = {
                            isShowTextEditor = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        }
        BuildChooseBoxOptions(
            onAttachImageClickListener = {
                photoPicker.launch(PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                ))
            },
            onAttachTextClickListener = {
                isShowTextEditor = true
            }
        )
    }
}

@Composable
fun BuildChooseBoxOptions(
    modifier: Modifier = Modifier,
    onAttachImageClickListener: () -> Unit,
    onAttachTextClickListener:()->Unit,
) {
    var isShowSpinnerChooseImageOrText by remember{mutableStateOf(false)}
    Box(
        modifier = modifier,
    ){
        IconButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                isShowSpinnerChooseImageOrText = !isShowSpinnerChooseImageOrText
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = if(isShowSpinnerChooseImageOrText) Icons.Rounded.Close else Icons.Rounded.AddCircle,
                contentDescription = "Choose image or text"
            )
        }
        DropdownMenu(
            expanded = isShowSpinnerChooseImageOrText,
            onDismissRequest = { isShowSpinnerChooseImageOrText = false }) {
            DropdownMenuItem(onClick = {
                isShowSpinnerChooseImageOrText = false
                onAttachTextClickListener.invoke()
            }) {
                Text(text = "Attach text")
            }
            DropdownMenuItem(onClick = {
                isShowSpinnerChooseImageOrText = false
                onAttachImageClickListener.invoke()
            }) {
                Text(text = "Attach image")
            }
        }
    }
}