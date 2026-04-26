package com.example.lnm.payments.domain.entities.payment_method

sealed interface PaymentMethod {
    val type: PaymentMethodType
}