package com.example.lnm.core.network.utils.network_result

sealed class NetworkResult<T> {
    data class Success<T>(
        val data: T,
        val statusCode: Int,
        val headers: Map<String, String>
    ) : NetworkResult<T>()

    data class Failed<T>(
        val data: T?,
        val statusCode: Int?,
        val throwable: Throwable?
    ) : NetworkResult<T>()
}