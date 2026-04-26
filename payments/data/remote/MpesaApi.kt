package com.example.lnm.payments.data.remote

import com.example.lnm.payments.data.model.request.LipaNaMpesaRequest
import com.example.lnm.payments.data.model.response.LipaNaMpesaResponse
import com.example.lnm.payments.data.model.response.MpesaTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MpesaApi {

    // 1. Generate access token
    @GET("oauth/v1/generate?grant_type=client_credentials")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String
    ): Response<MpesaTokenResponse>

    // 2. STK Push (Lipa na Mpesa)
    @POST("mpesa/stkpush/v1/processrequest")
    suspend fun lipaNaMpesa(
        @Header("Authorization") authorization: String,
        @Body request: LipaNaMpesaRequest
    ): Response<LipaNaMpesaResponse>

}