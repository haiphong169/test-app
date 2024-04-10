package com.haiphong.mentalhealthapp.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haiphong.mentalhealthapp.model.Post
import com.haiphong.mentalhealthapp.model.Topic


@Composable
fun TopicCard(topic: Topic, toTopic: (String) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .height(100.dp)
        .fillMaxWidth()
        .clickable { toTopic(topic.name) }) {
        Text(
            text = topic.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PostCard(post: Post, toPost: (String, String) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .height(100.dp)
        .fillMaxWidth()
        .clickable { toPost(post.title, post.topic) }) {
        Text(
            text = post.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        )
    }
}