package com.example.lnm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lnm.payments.presentation.features.make_payment_request.screens.MakePaymentRequestScreen
import com.example.lnm.payments.presentation.features.view_payment_requests.events.ViewPaymentRequestsEvent
import com.example.lnm.payments.presentation.features.view_payment_requests.screens.ViewPaymentRequestsScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.ViewPaymentRequests.route
    ) {
        composable(Destinations.MakePaymentRequest.route) {
            MakePaymentRequestScreen()
        }

        composable(Destinations.ViewPaymentRequests.route) {
            ViewPaymentRequestsScreen {
                when (it) {
                    ViewPaymentRequestsEvent.AddPaymentRequestClicked -> navController.navigate(
                        Destinations.MakePaymentRequest.route
                    )
                }
            }
        }
    }
}