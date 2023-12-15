package com.ubalia.call.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostsViewModel: ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    val posts = MutableLiveData<List<Post>>()

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                val postsList = PostsApi.retrofitService.getPosts()
                _status.value = "Success: ${postsList}"
                posts.value = postsList
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    fun getPost(id: Int): LiveData<Post> {
        val post = MutableLiveData<Post>()
        viewModelScope.launch {
            try {
                post.value = PostsApi.retrofitService.getPost(id)
            } catch (e: Exception) {
                e.message?.let { Log.e("PostsViewModel", it) }
            }
        }
        return post
    }

    fun publishPost(post: Post): LiveData<Post> {
        val retrievedPost = MutableLiveData<Post>()
        viewModelScope.launch {
            try {
                retrievedPost.value = PostsApi.retrofitService.publishPost(post)
            } catch (e: Exception) {
                e.message?.let { Log.e("PostsViewModel", it) }
            }
        }
        return retrievedPost
    }

    fun updatePost(post: Post): LiveData<Post> {
        val retrievedPost = MutableLiveData<Post>()
        viewModelScope.launch {
            try {
                Log.d("PostsViewModel", "Updating post: $post")
                retrievedPost.value = PostsApi.retrofitService.updatePost(post.id!!, post)
            } catch (e: Exception) {
                e.message?.let { Log.e("PostsViewModel", it) }
            }
        }
        return retrievedPost
    }
}