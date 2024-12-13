package com.example.androidcookbook.domain.usecase

import com.example.androidcookbook.data.modules.IoDispatcher
import com.example.androidcookbook.data.repositories.PostRepository
import com.example.androidcookbook.domain.model.post.Post
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeletePostUseCase @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val postRepository: PostRepository
): UseCase<Int, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameters: Int) {
        postRepository.deletePost(parameters)
    }
}
