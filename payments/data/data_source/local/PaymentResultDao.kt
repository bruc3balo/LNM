package com.example.lnm.payments.data.data_source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.lnm.payments.data.model.local.LocalPaymentResult
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentResultDao {

    @Upsert
    suspend fun savePaymentResult(paymentResult: LocalPaymentResult) : LocalPaymentResult

    @Query("DELETE FROM payment_results WHERE transactionId = :transactionId")
    suspend fun deletePaymentResultByTransactionId(transactionId: String)

    @Query("SELECT * FROM payment_results ORDER BY date DESC")
    fun getPaymentResultsOrderedByDate(): Flow<List<LocalPaymentResult>>

}