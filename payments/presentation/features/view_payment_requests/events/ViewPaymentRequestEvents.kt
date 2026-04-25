package com.example.lnm.payments.presentation.features.view_payment_requests.events

sealed interface ViewPaymentRequestsEvent {
    object AddPaymentRequestClicked : ViewPaymentRequestsEvent
}