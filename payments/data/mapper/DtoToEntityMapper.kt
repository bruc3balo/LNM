package com.example.lnm.payments.data.mapper

import com.example.lnm.payments.data.model.response.MpesaTokenResponse
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.entities.PaymentStatus
import kotlin.time.Duration.Companion.milliseconds

fun MpesaTokenResponse.toEntity(): MpesaToken {
    return MpesaToken(
        accessToken = accessToken,
        expiresIn = expiresInMs.toLong().milliseconds
    )
}

fun MpesaResponse.toDomain(): PaymentResult {
    return PaymentResult(
        transactionId = this,
        status = PaymentStatus.PENDING
    )
}