package com.example.lnm.payments.presentation.features.view_payment_requests.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.use_cases.DeletePaymentResultsUseCase
import com.example.lnm.payments.domain.use_cases.GetPaymentResultsUseCase
import com.example.lnm.payments.presentation.features.view_payment_requests.model.ViewPaymentRequestsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPaymentRequestsViewModel @Inject constructor(
    private val getPaymentResultsUseCase: GetPaymentResultsUseCase,
    private val deletePaymentResultsUseCase: DeletePaymentResultsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ViewPaymentRequestsState(paymentResults = emptyList()))
    val uiState = _uiState.asStateFlow()

    init {
        loadResults()
    }


    fun loadResults() {
        if (uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getPaymentResultsUseCase.execute(Unit)) {
                is TaskResult.FailedResult<List<PaymentResult>> -> {
                    _uiState.update { it.copy(isLoading = false) }
                }

                is TaskResult.SuccessResult<List<PaymentResult>> -> {
                    _uiState.update { it.copy(isLoading = false, paymentResults = result.data) }
                }
            }
        }
    }

    fun deleteResult(paymentResult: PaymentResult) {
        if (uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (deletePaymentResultsUseCase.execute(paymentResult)) {
                is TaskResult.FailedResult<Unit> -> {
                    _uiState.update { it.copy(isLoading = false) }
                }

                is TaskResult.SuccessResult<Unit> -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            paymentResults = it.paymentResults.filter { i -> i != paymentResult })
                    }
                }
            }
        }
    }

}