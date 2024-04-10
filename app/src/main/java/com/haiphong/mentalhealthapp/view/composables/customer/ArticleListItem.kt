package com.haiphong.mentalhealthapp.view.composables.customer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.haiphong.mentalhealthapp.model.Article

@Composable
fun ArticleListItem(article: Article, toArticle: (String) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { toArticle(article.url) }) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(text = article.content.substring(0, 20), style = MaterialTheme.typography.labelSmall)
    }
}