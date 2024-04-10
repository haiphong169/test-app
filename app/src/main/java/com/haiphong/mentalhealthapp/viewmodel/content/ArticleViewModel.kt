package com.haiphong.mentalhealthapp.viewmodel.content

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.haiphong.mentalhealthapp.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticleViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _article = MutableStateFlow(Article())
    val article = _article.asStateFlow()

    private val topicName: String = checkNotNull(savedStateHandle["topicName"])
    private val articleTitle: String = checkNotNull(savedStateHandle["articleTitle"])

//    init {
//        getArticle()
//    }
//
//    private fun getArticle() {
//        for (topic in topics) {
//            if (topic.name == topicName) {
//                for (article in topic.articlesList) {
//                    if (article.title == articleTitle) {
//                        _article.value = article
//                    }
//                }
//            }
//        }
//    }
}