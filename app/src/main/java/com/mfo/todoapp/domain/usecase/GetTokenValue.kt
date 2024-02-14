package com.mfo.todoapp.domain.usecase

import com.mfo.todoapp.domain.Repository
import javax.inject.Inject

class GetTokenValue @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token: String) = repository.getTokenValue(token)
}