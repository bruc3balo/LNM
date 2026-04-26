package com.example.lnm.payments.data.remote

import com.example.lnm.core.network.utils.network_result.NetworkResult
import com.example.lnm.core.network.utils.sendRetrofitRequest
import com.example.lnm.payments.data.model.request.LipaNaMpesaRequest
import com.example.lnm.payments.data.model.response.LipaNaMpesaResponse
import com.example.lnm.payments.data.model.response.MpesaTokenResponse
import javax.inject.Inject

class MpesaNetworkRequests @Inject constructor(
    private val api: MpesaApi
) {

    suspend fun sendGetTokenRequest(authorization: String): NetworkResult<MpesaTokenResponse> {
        return sendRetrofitRequest {
            api.getAccessToken(authorization = "Basic $authorization")
        }
    }

    suspend fun sendLipaNaMpesaRequest(
        authorization: String,
        request: LipaNaMpesaRequest
    ): NetworkResult<LipaNaMpesaResponse> {
        return sendRetrofitRequest {
            api.lipaNaMpesa(authorization = "Bearer $authorization", request = request)
        }
    }

}
