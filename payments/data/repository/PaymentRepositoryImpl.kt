package com.example.lnm.payments.data.repository

import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.data.service.MpesaPaymentProcessorService
import com.example.lnm.payments.domain.entities.PaymentRequest
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.entities.payment_method.MpesaPaymentMethod
import com.example.lnm.payments.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val mpesaPaymentProcessorService: MpesaPaymentProcessorService
) : PaymentRepository {

    override suspend fun initiatePayment(request: PaymentRequest): TaskResult<PaymentResult> {
        return when (val method = request.method) {
            is MpesaPaymentMethod -> mpesaPaymentProcessorService.makeLNMPayment(
                authRequest = method.authRequest,
                account = method.account,
                amount = method.amount,
                shortCode = method.shortCode,
                callBackUrl = method.callbackUrl,
                passKey = method.passKey,
                description = method.description,
                phoneNumber = method.number
            )
        }
    }

}