package com.example.lnm.payments.data.data_source.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomPaymentModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PaymentResultDatabase {
        return Room.databaseBuilder(
            context,
            PaymentResultDatabase::class.java,
            "lnm_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePaymentResultDao(
        database: PaymentResultDatabase
    ): PaymentResultDao {
        return database.dao
    }

}