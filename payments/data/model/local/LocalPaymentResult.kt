package com.example.lnm.payments.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lnm.payments.domain.entities.PaymentStatus
import java.time.LocalDateTime

@Entity(tableName = "payment_results")
data class LocalPaymentResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(index = true)
    val transactionId: String,

    val message: String,

    val date: LocalDateTime,

    val status: PaymentStatus
)
