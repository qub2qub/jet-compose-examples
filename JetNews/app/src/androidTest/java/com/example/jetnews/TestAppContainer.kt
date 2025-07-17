package com.example.jetnews

import android.content.Context
import com.example.jetnews.data.AppContainer
import com.example.jetnews.data.interests.InterestsRepository
import com.example.jetnews.data.interests.impl.FakeInterestsRepository
import com.example.jetnews.data.posts.PostsRepository
import com.example.jetnews.data.posts.impl.BlockingFakePostsRepository

class TestAppContainer(private val context: Context) : AppContainer {

    override val postsRepository: PostsRepository by lazy {
        BlockingFakePostsRepository()
    }

    override val interestsRepository: InterestsRepository by lazy {
        FakeInterestsRepository()
    }
}
