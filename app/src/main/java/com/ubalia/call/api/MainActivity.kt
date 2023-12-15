package com.ubalia.call.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val viewModel: PostsViewModel by viewModels()

    private var postToUpdate: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        viewModel.status.observe(this, {
            Log.d(TAG, "$it")
        })
         */

        viewModel.posts.observe(this) {
            Log.d(TAG, it[0].title)
        }

        viewModel.getPost(3).observe(this) {
            Log.d(TAG, "Post retrieved: $it")
            postToUpdate = it

            val postToSend = Post(
                postToUpdate!!.userId,
                postToUpdate!!.id,
                "Updated title",
                postToUpdate!!.body
            )
            viewModel.updatePost(postToSend).observe(this) {
                Log.d(TAG, "Post update response: $it")
            }
        }

        val post = Post(
            1,
            null,
            "my title",
            "bla bla bla"
        )

        viewModel.publishPost(post).observe(this) {
            Log.d(TAG, "Post publish response: $it")
        }
    }
}