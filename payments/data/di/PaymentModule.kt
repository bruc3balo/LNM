package com.example.lnm.payments.data.di

import com.example.lnm.payments.data.repository.PaymentRepositoryImpl
import com.example.lnm.payments.domain.repository.PaymentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PaymentModule {

    @Binds
    abstract fun bindPaymentRepository(
        impl: PaymentRepositoryImpl
    ): PaymentRepository
}