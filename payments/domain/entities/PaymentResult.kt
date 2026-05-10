package com.example.lnm.payments.domain.entities

import java.time.LocalDateTime

class PaymentResult(
    val transactionId: String,
    val status: PaymentStatus,
    val date: LocalDateTime,
    val message: String
)