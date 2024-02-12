package com.mfo.todoapp.domain

interface Repository {
    suspend fun getAll(authorization: String)
}