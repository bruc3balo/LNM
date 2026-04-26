package com.example.lnm.payments.data.remote

import android.util.Base64
import com.example.lnm.core.common.failure.Failure
import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.core.network.utils.network_result.NetworkResult
import com.example.lnm.payments.data.model.request.LipaNaMpesaRequest
import com.example.lnm.payments.data.model.response.LipaNaMpesaResponse
import com.example.lnm.payments.data.model.response.MpesaTokenResponse
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

typealias IssuedAt = Date

class MpesaRemoteDataSource @Inject constructor(
    private val mpesaNetworkRequests: MpesaNetworkRequests
) {
    private var cachedToken: Pair<IssuedAt, MpesaTokenResponse>? = null

    suspend fun lipaNaMpesa(
        request: LipaNaMpesaRequest
    ): TaskResult<LipaNaMpesaResponse> {
        try {
            return when (val tokenResponse = getToken()) {
                is TaskResult.FailedResult<MpesaTokenResponse> -> TaskResult.FailedResult(
                    tokenResponse.failure
                )

                is TaskResult.SuccessResult<MpesaTokenResponse> -> {
                    val paymentResult = mpesaNetworkRequests.sendLipaNaMpesaRequest(
                        authorization = tokenResponse.data.accessToken,
                        request = request
                    )

                    when (paymentResult) {
                        is NetworkResult.Failed<LipaNaMpesaResponse> -> TaskResult.FailedResult(
                            failure = Failure(
                                exception = paymentResult.throwable
                            )
                        )

                        is NetworkResult.Success<LipaNaMpesaResponse> -> TaskResult.SuccessResult(
                            data = paymentResult.data,
                            message = "Request completed"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            return TaskResult.FailedResult(failure = Failure(exception = e))
        }
    }
    suspend fun getToken(): TaskResult<MpesaTokenResponse> {
        try {

            if (canReuseToken()) {
                return TaskResult.SuccessResult(
                    data = cachedToken!!.second,
                    message = "Token reused"
                )
            }

            // TODO: Inject Credentials
            val credentials = "consumerKey:consumerSecret"
            val encodedAuthorizationToken = Base64.encodeToString(
                credentials.toByteArray(),
                Base64.NO_WRAP
            )

            val result = mpesaNetworkRequests.sendGetTokenRequest(
                authorization = encodedAuthorizationToken
            )

            return when (result) {
                is NetworkResult.Failed<MpesaTokenResponse> -> TaskResult.FailedResult(
                    failure = Failure(
                        exception = result.throwable
                    )
                )

                is NetworkResult.Success<MpesaTokenResponse> -> {
                    cachedToken = Date() to result.data
                    TaskResult.SuccessResult(data = result.data, message = "New token obtained")
                }
            }
        } catch (e: Exception) {
            return TaskResult.FailedResult(failure = Failure(exception = e))
        }
    }
    private fun canReuseToken(): Boolean {
        val cached = cachedToken ?: return false

        val issuedAt: IssuedAt = cached.first
        val token = cached.second

        val expiryDuration = token.expiresInMs.toLong().milliseconds
        val expiryTime = issuedAt.time + expiryDuration.inWholeMilliseconds

        val now = System.currentTimeMillis()
        val timeRemaining = expiryTime - now

        return timeRemaining.milliseconds > 5.minutes
    }

}