package com.example.lnm.payments.data.data_source.local

import com.example.lnm.payments.data.model.local.LocalPaymentResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentResultLocalDataSource @Inject constructor(
    private val paymentResultDao: PaymentResultDao
) {

    suspend fun storePaymentResult(paymentResult: LocalPaymentResult): LocalPaymentResult {
        return paymentResultDao.savePaymentResult(paymentResult)
    }

    suspend fun deletePaymentResultByTransactionId(transactionId: String) {
        return paymentResultDao.deletePaymentResultByTransactionId(transactionId)
    }

    fun getPaymentResultsOrderedByDate(): Flow<List<LocalPaymentResult>> {
        return paymentResultDao.getPaymentResultsOrderedByDate()
    }
}
