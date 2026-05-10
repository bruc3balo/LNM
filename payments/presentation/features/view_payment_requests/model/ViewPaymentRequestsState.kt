package com.example.lnm.payments.presentation.features.view_payment_requests.model

import com.example.lnm.payments.domain.entities.PaymentResult

data class ViewPaymentRequestsState(
    val paymentResults: List<PaymentResult>,
    val isLoading: Boolean = false
)
