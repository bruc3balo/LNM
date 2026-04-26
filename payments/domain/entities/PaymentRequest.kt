package com.example.lnm.payments.domain.entities

import com.example.lnm.payments.domain.entities.payment_method.PaymentMethod

class PaymentRequest(
    val method: PaymentMethod,
)