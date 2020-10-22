package com.os.busservice.model.loginResponse

data class LoginResponse(
    val msg: String,
    val result: LoginResult?,
    val success: Boolean,
    val token: String?,
    val accountNotExist: String?
)