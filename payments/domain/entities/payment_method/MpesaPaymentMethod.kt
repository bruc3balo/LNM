package com.example.lnm.payments.domain.entities.payment_method

import com.example.lnm.payments.data.model.request.DarajaAuthenticationRequest
import com.example.lnm.payments.domain.value_objects.DarajaPassKey
import com.example.lnm.payments.domain.value_objects.Money
import com.example.lnm.payments.domain.value_objects.MpesaAccount
import com.example.lnm.payments.domain.value_objects.MpesaPaymentDescription
import com.example.lnm.payments.domain.value_objects.MpesaPhoneNumber
import com.example.lnm.payments.domain.value_objects.MpesaShortCode
import java.net.URI

class MpesaPaymentMethod(
    val amount: Money,
    val number: MpesaPhoneNumber,
    val account: MpesaAccount,
    val callbackUrl: URI,
    val authRequest: DarajaAuthenticationRequest,
    val passKey: DarajaPassKey,
    val shortCode: MpesaShortCode,
    val description: MpesaPaymentDescription,
    override val type: PaymentMethodType = PaymentMethodType.MPESA,
) : PaymentMethod