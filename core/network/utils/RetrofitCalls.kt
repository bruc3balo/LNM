package com.example.lnm.core.network.utils

import com.example.lnm.core.network.utils.network_result.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> sendRetrofitRequest(
    call: suspend () -> Response<T>
): NetworkResult<T> {

    return try {

        val response = call()

        val body = response.body()
        val headers = response.headers().toMultimap().mapValues { it.value.firstOrNull().orEmpty() }

        if (response.isSuccessful) {
            return NetworkResult.Success(
                data = body!!,
                statusCode = response.code(),
                headers = headers,
            )
        }

        NetworkResult.Failed(
            data = body,
            statusCode = response.code(),
            throwable = null,
        )
    } catch (e: HttpException) {

        NetworkResult.Failed(
            data = null,
            statusCode = e.code(),
            throwable = e,
        )

    } catch (e: IOException) {

        NetworkResult.Failed(
            data = null,
            statusCode = null,
            throwable = e,
        )

    } catch (e: Exception) {

        NetworkResult.Failed(
            data = null,
            statusCode = null,
            throwable = e,
        )
    }
}
