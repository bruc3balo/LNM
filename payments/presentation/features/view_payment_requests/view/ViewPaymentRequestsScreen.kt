package com.example.lnm.payments.presentation.features.view_payment_requests.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.presentation.features.view_payment_requests.model.ViewPaymentRequestsEvent
import com.example.lnm.payments.presentation.features.view_payment_requests.viewmodel.ViewPaymentRequestsViewModel

@Composable
fun ViewPaymentRequestsScreen(
    viewModel: ViewPaymentRequestsViewModel = hiltViewModel(),
    onEvent: (ViewPaymentRequestsEvent) -> Unit
) {

    val state = viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            GoToAddPaymentRequestScreenFAB {
                onEvent(ViewPaymentRequestsEvent.AddPaymentRequestClicked)
            }
        }
    ) {
        Text(text = "View Payment Requests Screen", modifier = Modifier.padding(it))

        PaymentResultsList(
            paymentResults = state.value.paymentResults,
            onDelete = viewModel::deleteResult
        )
    }
}


@Composable
private fun PaymentResultsList(
    paymentResults: List<PaymentResult>,
    onDelete: (PaymentResult) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        items(paymentResults) {
            PaymentResultScreen(
                paymentResult = it,
                onDelete = onDelete,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun PaymentResultScreen(
    paymentResult: PaymentResult,
    onDelete: (PaymentResult) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(text = paymentResult.transactionId)
        Text(text = paymentResult.message)
        Text(text = paymentResult.status.name)
        Text(text = paymentResult.date.toString())
        IconButton(
            onClick = { onDelete(paymentResult) }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete result",
            )
        }
    }
}

@Composable
fun GoToAddPaymentRequestScreenFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {

    }
}