package com.haiphong.mentalhealthapp.view.screens.customer.content

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.customer.ArticleListItem
import com.haiphong.mentalhealthapp.view.composables.PostCard
import com.haiphong.mentalhealthapp.viewmodel.content.TopicViewModel

@Composable
fun TopicScreen(
    viewModel: TopicViewModel = viewModel(),
    toPost: (String, String) -> Unit,
    toArticle: (String, String) -> Unit
) {
    val postsList by viewModel.postsList.collectAsState()
    val articlesState by viewModel.articlesState.collectAsState()
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(items = postsList, key = { post -> post.title }) { post ->
            PostCard(post = post, toPost = toPost)
        }
        item {
            Text(text = "Articles", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))
        }
        when (articlesState.status) {
            "loading" -> {
                item {
                    CircularProgressIndicator()
                }
            }
            "done" -> {
                items(articlesState.articlesList) {
                    ArticleListItem(article = it) { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                }
            }
            "error" -> {
                item {
                    Text(text = "Error")
                }
            }
        }
    }
}