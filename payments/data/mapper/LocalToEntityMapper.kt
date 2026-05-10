package com.example.lnm.payments.data.mapper

import com.example.lnm.payments.data.model.local.LocalPaymentResult
import com.example.lnm.payments.domain.entities.PaymentResult

fun LocalPaymentResult.toEntity() : PaymentResult {
    return PaymentResult(
        transactionId = transactionId,
        status = status,
        date = date,
        message = message
    )
}