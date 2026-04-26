package com.example.lnm.payments.domain.entities

import com.example.lnm.payments.domain.value_objects.Money

class PaymentRequest(
    val amount: Money,
    val method: PaymentMethod,
    val reference: String,
    val description: String
)