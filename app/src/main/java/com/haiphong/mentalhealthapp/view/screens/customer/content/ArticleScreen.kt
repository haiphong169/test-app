package com.haiphong.mentalhealthapp.view.screens.customer.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.viewmodel.content.ArticleViewModel

@Composable
fun ArticleScreen(articleViewModel: ArticleViewModel = viewModel()) {
    val article by articleViewModel.article.collectAsState()
    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
        Text(text = article.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(20.dp))
//        Text(text = article.author, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(200.dp))
        Text(text = article.content)
    }

}