package com.example.lnm.payments.presentation.features.view_payment_requests.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.presentation.features.view_payment_requests.model.ViewPaymentRequestsEvent
import com.example.lnm.payments.presentation.features.view_payment_requests.viewmodel.ViewPaymentRequestsViewModel
import java.util.Locale

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
        Column(modifier = Modifier.padding(it)) {
            Text(text = "View Payment Requests Screen", modifier = Modifier.paddingFromBaseline(top = 8.dp))

            PaymentResultsList(
                paymentResults = state.value.paymentResults,
                onDelete = viewModel::deleteResult,
                modifier = Modifier.padding(2.dp).weight(1F)
            )
        }
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
    Card(modifier.padding(4.dp)) {
        Column(
            Modifier.padding(4.dp)
        ) {
            Text(text = paymentResult.transactionId)
            HorizontalDivider(color = Color.Gray, thickness = 2.dp)
            Text(text = paymentResult.message)
            Text(text = paymentResult.status.name.lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            HorizontalDivider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 2.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = paymentResult.date.toString())
                Spacer(Modifier.weight(1F))
                IconButton(
                    onClick = { onDelete(paymentResult) }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        tint = Color.Red,
                        contentDescription = "Delete result",
                    )
                }
            }

        }
    }
}

@Composable
fun GoToAddPaymentRequestScreenFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Add payment request",
        )
    }
}