package com.example.lnm.payments.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lnm.core.database.Converters
import com.example.lnm.payments.data.model.local.LocalPaymentResult

@Database(entities = [LocalPaymentResult::class], version = 1)
@TypeConverters(Converters::class)
abstract class PaymentResultDatabase : RoomDatabase() {
    abstract val dao: PaymentResultDao
}