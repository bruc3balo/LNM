package com.example.lnm.payments.data.repository

import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.data.remote.MpesaRemoteDataSource
import com.example.lnm.payments.domain.entities.MpesaPaymentMethod
import com.example.lnm.payments.domain.entities.PaymentRequest
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val mpesaRemoteDataSource: MpesaRemoteDataSource
) : PaymentRepository {

    override suspend fun initiatePayment(request: PaymentRequest): TaskResult<PaymentResult> {
        when(request.method) {
            //TODO: MpesaPaymentProcessor
            is MpesaPaymentMethod -> TODO()
        }
    }

}