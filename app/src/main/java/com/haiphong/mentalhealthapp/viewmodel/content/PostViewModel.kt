package com.haiphong.mentalhealthapp.viewmodel.content

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.haiphong.mentalhealthapp.model.Post
import com.haiphong.mentalhealthapp.model.topics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    private val _postState = MutableStateFlow(Post("", "", ""))
    val postState = _postState.asStateFlow()

    private val topicName = checkNotNull(savedStateHandle["topicName"])
    private val postTitle = checkNotNull(savedStateHandle["postTitle"])


    init {
        getPost()
    }

    private fun getPost() {
        for(topic in topics) {
            if(topic.name == topicName) {
                for(post in topic.postsList) {
                    if(post.title == postTitle) {
                        _postState.value = post
                        Log.d("Get post", "found the post")
                    }
                }
            }
        }
    }
}