package com.example.lnm.payments.presentation.features.make_payment_request.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lnm.payments.domain.entities.payment_method.PaymentMethodType
import com.example.lnm.payments.presentation.features.make_payment_request.state.MakeMpesaPaymentForm
import com.example.lnm.payments.presentation.features.make_payment_request.state.MakePaymentRequestState
import com.example.lnm.payments.presentation.features.make_payment_request.viewmodel.MakePaymentRequestViewModel

@Composable
fun MakePaymentRequestScreen(
    viewModel: MakePaymentRequestViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {

        }
    ) {
        Column(modifier = Modifier.padding(it)) {


            PaymentMethodSelector(
                selected = state.value.paymentMethod,
                onPaymentMethodTypeChanged = viewModel::onPaymentMethodTypeChanged
            )

            when (val paymentMethod = state.value.paymentForm) {
                is MakeMpesaPaymentForm -> MpesaPaymentScreen(
                    isLoading = state.value.isLoading,
                    paymentForm = paymentMethod,
                    state = state.value,
                    onPhoneChanged = viewModel::onPhoneChanged,
                    onAmountChanged = viewModel::onAmountChanged,
                    onAccountChanged = viewModel::onAccountChanged,
                    onCallbackUrlChanged = viewModel::onCallbackUrlChanged,
                    onDescriptionChanged = viewModel::onDescriptionChanged,
                    onShortCodeChanged = viewModel::onShortCodeChanged,
                    onPassKeyChanged = viewModel::onPassKeyChanged,
                    onConsumerKeyChanged = viewModel::onConsumerKeyChanged,
                    onConsumerSecretChanged = viewModel::onConsumerSecretChanged,
                    onPayClicked = viewModel::initiatePayment
                )

                null -> Text("Select a payment method")
            }

            state.value.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

        }
    }
}

@Composable
fun PaymentMethodSelector(
    selected: PaymentMethodType?,
    onPaymentMethodTypeChanged: (PaymentMethodType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PaymentMethodType.entries.forEach { method ->

            val isSelected = method == selected

            Button(
                onClick = { onPaymentMethodTypeChanged(method) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Text(method.name)
            }
        }
    }
}

@Composable
fun MpesaPaymentScreen(
    isLoading: Boolean,
    paymentForm: MakeMpesaPaymentForm,
    state: MakePaymentRequestState,
    onPhoneChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onAccountChanged: (String) -> Unit,
    onCallbackUrlChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onShortCodeChanged: (String) -> Unit,
    onPassKeyChanged: (String) -> Unit,
    onConsumerKeyChanged: (String) -> Unit,
    onConsumerSecretChanged: (String) -> Unit,
    onPayClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = paymentForm.amount,
            onValueChange = onAmountChanged,
            label = { Text("Amount") },
            singleLine = true
        )


        OutlinedTextField(
            value = paymentForm.phone,
            onValueChange = onPhoneChanged,
            label = { Text("Phone Number") },
            singleLine = true
        )

        OutlinedTextField(
            value = paymentForm.account,
            onValueChange = onAccountChanged,
            label = { Text("Account") },
            singleLine = true
        )

        OutlinedTextField(
            value = paymentForm.callbackUrl,
            onValueChange = onCallbackUrlChanged,
            label = { Text("Callback url") },
            singleLine = true
        )


        OutlinedTextField(
            value = paymentForm.description,
            onValueChange = onDescriptionChanged,
            label = { Text("Description") },
            singleLine = true
        )

        OutlinedTextField(
            value = paymentForm.shortCode,
            onValueChange = onShortCodeChanged,
            label = { Text("Short code") },
            singleLine = true
        )

        OutlinedTextField(
            value = paymentForm.passKey,
            onValueChange = onPassKeyChanged,
            label = { Text("Pass key") },
            singleLine = true
        )

        OutlinedTextField(
            value = paymentForm.consumerKey,
            onValueChange = onConsumerKeyChanged,
            label = { Text("Consumer key") },
            singleLine = true
        )

        OutlinedTextField(
            value = paymentForm.consumerSecret,
            onValueChange = onConsumerSecretChanged,
            label = { Text("Consumer Secret") },
            singleLine = true
        )

        if (!isLoading) Button(
            onClick = onPayClicked,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isLoading) "Processing..." else "Pay with M-Pesa")
        } else CircularProgressIndicator()

    }
}