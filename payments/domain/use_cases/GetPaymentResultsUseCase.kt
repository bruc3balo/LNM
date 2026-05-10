package com.example.lnm.payments.domain.use_cases

import com.example.lnm.core.common.base_use_case.BaseUseCase
import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.repository.PaymentRepository
import javax.inject.Inject

class GetPaymentResultsUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) : BaseUseCase<Unit, List<PaymentResult>> {

    override suspend fun execute(input: Unit): TaskResult<List<PaymentResult>> {
        return paymentRepository.getPaymentResults()
    }

}
