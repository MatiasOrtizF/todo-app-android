package com.mfo.todoapp.data.preferences

interface Preferences {
    suspend fun putTokenValue(token: String)
    suspend fun getTokenValue(token: String): String?
}