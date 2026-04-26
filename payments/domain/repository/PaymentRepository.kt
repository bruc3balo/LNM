package com.example.lnm.payments.domain.repository

import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.domain.entities.PaymentRequest
import com.example.lnm.payments.domain.entities.PaymentResult

interface PaymentRepository {
    suspend fun initiatePayment(request: PaymentRequest): TaskResult<PaymentResult>
}