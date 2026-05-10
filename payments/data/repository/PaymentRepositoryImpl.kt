package com.example.lnm.payments.data.repository

import com.example.lnm.core.common.failure.Failure
import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.data.data_source.local.PaymentResultLocalDataSource
import com.example.lnm.payments.data.mapper.toEntity
import com.example.lnm.payments.data.model.local.LocalPaymentResult
import com.example.lnm.payments.data.service.MpesaPaymentProcessorService
import com.example.lnm.payments.domain.entities.PaymentRequest
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.entities.payment_method.MpesaPaymentMethod
import com.example.lnm.payments.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val mpesaPaymentProcessorService: MpesaPaymentProcessorService,
    private val paymentResultLocalDataSource: PaymentResultLocalDataSource
) : PaymentRepository {

    override suspend fun initiatePayment(request: PaymentRequest): TaskResult<PaymentResult> {
        val paymentResultTask = when (val method = request.method) {
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

        when (paymentResultTask) {
            is TaskResult.SuccessResult<PaymentResult> -> {
                paymentResultLocalDataSource.storePaymentResult(
                    paymentResult = LocalPaymentResult(
                        transactionId = paymentResultTask.data.transactionId,
                        message = paymentResultTask.data.message,
                        date = paymentResultTask.data.date,
                        status = paymentResultTask.data.status
                    )
                )
            }

            else -> {
                print("Not saving failed request")
            }
        }


        return paymentResultTask
    }

    override suspend fun getPaymentResults(): TaskResult<List<PaymentResult>> {
        return try {
            val result = paymentResultLocalDataSource.getPaymentResultsOrderedByDate()
                .map { list -> list.map(LocalPaymentResult::toEntity) }
                .first()

            TaskResult.SuccessResult(data = result, message = "Fetched payment results")
        } catch (e: Exception) {
            TaskResult.FailedResult(Failure(exception = e))
        }
    }

    override suspend fun deletePaymentResult(transactionId: String): TaskResult<Unit> {
        return try {
            paymentResultLocalDataSource.deletePaymentResultByTransactionId(transactionId)
            TaskResult.SuccessResult(data = Unit, message = "Success")
        } catch (e: Exception) {
            TaskResult.FailedResult(Failure(exception = e))
        }
    }

}