package com.example.lnm.payments.presentation.features.make_payment_request.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lnm.payments.data.model.request.DarajaAuthenticationRequest
import com.example.lnm.payments.domain.entities.PaymentRequest
import com.example.lnm.payments.domain.entities.payment_method.MpesaPaymentMethod
import com.example.lnm.payments.domain.entities.payment_method.PaymentMethodType
import com.example.lnm.payments.domain.entities.payment_method.PaymentMethodType.MPESA
import com.example.lnm.payments.domain.use_cases.InitiatePaymentUseCase
import com.example.lnm.payments.domain.value_objects.DarajaPassKey
import com.example.lnm.payments.domain.value_objects.Money
import com.example.lnm.payments.domain.value_objects.MpesaAccount
import com.example.lnm.payments.domain.value_objects.MpesaPaymentDescription
import com.example.lnm.payments.domain.value_objects.MpesaPhoneNumber
import com.example.lnm.payments.domain.value_objects.MpesaShortCode
import com.example.lnm.payments.presentation.features.make_payment_request.state.MakeMpesaPaymentForm
import com.example.lnm.payments.presentation.features.make_payment_request.state.MakePaymentRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class MakePaymentRequestViewModel @Inject constructor(
    private val initiatePaymentUseCase: InitiatePaymentUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(value = MakePaymentRequestState())

    val uiState = _uiState.asStateFlow()

    fun onAmountChanged(amount: String) {
        _uiState.update { it.onAmountChanged(amount = amount) }
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update { it.onPhoneChanged(phone = phone) }
    }

    fun onAccountChanged(account: String) {
        _uiState.update { it.onAccountChanged(account = account) }
    }

    fun onCallbackUrlChanged(callbackUrl: String) {
        _uiState.update { it.onCallbackUrlChanged(callbackUrl = callbackUrl) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.onDescriptionChanged(description = description) }
    }

    fun onShortCodeChanged(shortCode: String) {
        _uiState.update { it.onShortCodeChanged(shortCode = shortCode) }
    }

    fun onPassKeyChanged(passKey: String) {
        _uiState.update { it.onPassKeyChanged(passKey = passKey) }
    }

    fun onConsumerKeyChanged(consumerKey: String) {
        _uiState.update { it.onConsumerKeyChanged(consumerKey = consumerKey) }
    }

    fun onConsumerSecretChanged(consumerSecret: String) {
        _uiState.update { it.onConsumerSecretChanged(consumerSecret = consumerSecret) }
    }

    fun onPaymentMethodTypeChanged(paymentMethod: PaymentMethodType) {
        _uiState.update {
            when (paymentMethod) {
                MPESA -> it.copy(paymentForm = MakeMpesaPaymentForm(), paymentMethod = paymentMethod)
            }
        }
    }

    fun initiatePayment() {
        if (_uiState.value.isLoading) return

        val method = when (val paymentForm = _uiState.value.paymentForm) {
            is MakeMpesaPaymentForm -> MpesaPaymentMethod(
                amount = Money(paymentForm.amount.toBigDecimal()),
                number = MpesaPhoneNumber.create(paymentForm.phone),
                account = MpesaAccount(paymentForm.account),
                callbackUrl = URI.create(paymentForm.callbackUrl),
                authRequest = DarajaAuthenticationRequest(
                    consumerKey = paymentForm.consumerKey,
                    consumerSecret = paymentForm.consumerSecret
                ),
                passKey = DarajaPassKey(paymentForm.passKey),
                description = MpesaPaymentDescription(paymentForm.description),
                shortCode = MpesaShortCode(paymentForm.shortCode),
            )

            null -> null
        }
        if (method == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = initiatePaymentUseCase.execute(
                input = PaymentRequest(method = method)
            )
            _uiState.update { it.onResult(result) }
        }
    }


}