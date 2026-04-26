package com.example.lnm.payments.presentation.features.make_payment_request.state

import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.entities.payment_method.PaymentMethodType

data class MakePaymentRequestState(
    val paymentMethod: PaymentMethodType? = null,
    val paymentForm: MakePaymentForm? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val paymentResult: PaymentResult? = null
) {
    val isSuccess: Boolean get() = paymentResult != null
    fun onResult(result: TaskResult<PaymentResult>): MakePaymentRequestState {
        return when (result) {
            is TaskResult.FailedResult<PaymentResult> -> copy(
                paymentResult = null,
                isLoading = false,
                errorMessage = result.failure.message
            )

            is TaskResult.SuccessResult<PaymentResult> -> copy(
                successMessage = result.message,
                paymentResult = result.data,
                isLoading = false,
                errorMessage = null
            )
        }
    }

    fun onAmountChanged(amount: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(amount = amount))
            null -> this
        }
    }

    fun onPhoneChanged(phone: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(phone = phone))
            null -> this
        }
    }

    fun onAccountChanged(account: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(account = account))
            null -> this
        }
    }

    fun onCallbackUrlChanged(callbackUrl: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(callbackUrl = callbackUrl))
            null -> this
        }
    }

    fun onDescriptionChanged(description: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(description = description))
            null -> this
        }
    }

    fun onShortCodeChanged(shortCode: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(shortCode = shortCode))
            null -> this
        }
    }

    fun onPassKeyChanged(passKey: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(passKey = passKey))
            null -> this
        }
    }

    fun onConsumerKeyChanged(consumerKey: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(consumerKey = consumerKey))
            null -> this
        }
    }

    fun onConsumerSecretChanged(consumerSecret: String) : MakePaymentRequestState {
        return when(paymentForm) {
            is MakeMpesaPaymentForm -> copy(paymentForm = paymentForm.copy(consumerSecret = consumerSecret))
            null -> this
        }
    }


}