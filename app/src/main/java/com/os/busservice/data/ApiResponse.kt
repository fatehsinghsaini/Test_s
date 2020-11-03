package com.os.busservice.data

import androidx.annotation.NonNull
import androidx.annotation.Nullable


class ApiResponse<T>(
    val status: Status, @param:Nullable @field:Nullable
    val data: T?,
    val error: Throwable?
) {

    fun loading(): ApiResponse<T> {
        return ApiResponse(Status.LOADING, null, null)
    }

    fun success(@NonNull data: T): ApiResponse<T> {
        return ApiResponse(Status.SUCCESS, data, null)
    }

    fun error(@NonNull error: Throwable): ApiResponse<T> {
        return ApiResponse(Status.ERROR, null, error)
    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }

}
