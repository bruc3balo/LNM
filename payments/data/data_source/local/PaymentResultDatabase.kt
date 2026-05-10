package com.example.lnm.payments.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lnm.payments.data.model.local.LocalPaymentResult

@Database(entities = [LocalPaymentResult::class], version = 1)
abstract class PaymentResultDatabase : RoomDatabase() {
    abstract val dao: PaymentResultDao
}