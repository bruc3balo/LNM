package com.example.lnm.navigation

sealed class Destinations(val route: String) {
    object MakePaymentRequest : Destinations("make_payment_request")

    object ViewPaymentRequests : Destinations("view_payment_requests")

}