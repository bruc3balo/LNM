package com.example.lnm.payments.presentation.features.view_payment_requests.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lnm.payments.presentation.features.view_payment_requests.events.ViewPaymentRequestsEvent

@Composable
fun ViewPaymentRequestsScreen(
    onEvent: (ViewPaymentRequestsEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            GoToAddPaymentRequestScreenFAB {
                onEvent(ViewPaymentRequestsEvent.AddPaymentRequestClicked)
            }
        }
    ) {
        Text(text = "View Payment Requests Screen", modifier = Modifier.padding(it))
    }
}


@Composable
fun GoToAddPaymentRequestScreenFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {

    }
}