package com.example.lnm.payments.domain.entities

import com.example.lnm.payments.domain.value_objects.MpesaPhoneNumber

sealed interface PaymentMethod

class MpesaPaymentMethod(
    val number: MpesaPhoneNumber
) : PaymentMethod