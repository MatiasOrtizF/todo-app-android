package com.mfo.todoapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.mfo.todoapp.domain.model.LoginModel
import com.mfo.todoapp.domain.model.User

data class LoginResponse (@SerializedName("token") val token: String, @SerializedName("user")  val user: User) {
    fun toDomain(): LoginModel {
        return LoginModel(
            token = token,
            user = user
        )
    }
}