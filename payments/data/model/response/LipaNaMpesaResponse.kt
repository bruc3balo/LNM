package com.example.lnm.payments.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LipaNaMpesaResponse(
    @SerialName("CheckoutRequestID")
    val checkoutRequestId: String,

    @SerialName("CustomerMessage")
    val customerMessage: String,

    @SerialName("MerchantRequestID")
    val merchantRequestId: String,

    @SerialName("ResponseCode")
    val responseCode: String,

    @SerialName("ResponseDescription")
    val responseDescription: String
)