package com.haiphong.mentalhealthapp.view.screens.customer.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.viewmodel.content.PostViewModel

@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    val post by viewModel.postState.collectAsState()

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = post.title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = post.content, modifier = Modifier.fillMaxWidth())
    }
}