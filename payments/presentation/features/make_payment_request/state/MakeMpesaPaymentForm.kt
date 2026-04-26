package com.example.lnm.payments.presentation.features.make_payment_request.state

data class MakeMpesaPaymentForm(
    val amount: String = "",
    val phone: String = "",
    val account: String = "",
    val callbackUrl: String = "",
    val description: String = "",
    val shortCode: String = "",
    val passKey: String = "",
    val consumerKey: String = "",
    val consumerSecret: String = "",
) : MakePaymentForm
