package com.example.lnm.payments.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MpesaTokenResponse(

    @SerialName("access_token")
    val accessToken: String,

    @SerialName("expires_in")
    val expiresInMs: String

)