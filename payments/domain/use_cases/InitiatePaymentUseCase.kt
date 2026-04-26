package com.example.lnm.payments.domain.use_cases

import com.example.lnm.core.common.base_use_case.BaseUseCase
import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.domain.entities.PaymentRequest
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.repository.PaymentRepository
import javax.inject.Inject

class InitiatePaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) : BaseUseCase<PaymentRequest, PaymentResult> {

    override suspend fun execute(input: PaymentRequest): TaskResult<PaymentResult> {
        return paymentRepository.initiatePayment(input)
    }

}