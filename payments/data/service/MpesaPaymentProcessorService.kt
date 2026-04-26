package com.example.lnm.payments.data.service

import android.util.Base64
import com.example.lnm.core.common.failure.Failure
import com.example.lnm.core.common.result.TaskResult
import com.example.lnm.payments.data.model.request.DarajaAuthenticationRequest
import com.example.lnm.payments.data.model.request.LipaNaMpesaRequest
import com.example.lnm.payments.data.model.response.LipaNaMpesaResponse
import com.example.lnm.payments.data.remote.MpesaRemoteDataSource
import com.example.lnm.payments.domain.entities.PaymentResult
import com.example.lnm.payments.domain.entities.PaymentStatus
import com.example.lnm.payments.domain.value_objects.DarajaPassKey
import com.example.lnm.payments.domain.value_objects.Money
import com.example.lnm.payments.domain.value_objects.MpesaAccount
import com.example.lnm.payments.domain.value_objects.MpesaPaymentDescription
import com.example.lnm.payments.domain.value_objects.MpesaPhoneNumber
import com.example.lnm.payments.domain.value_objects.MpesaShortCode
import java.net.URI
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class MpesaPaymentProcessorService @Inject constructor(
    private val mpesaRemoteDataSource: MpesaRemoteDataSource
) {
    suspend fun makeLNMPayment(
        authRequest: DarajaAuthenticationRequest,
        account: MpesaAccount,
        amount: Money,
        shortCode: MpesaShortCode,
        callBackUrl: URI,
        passKey: DarajaPassKey,
        description: MpesaPaymentDescription,
        phoneNumber: MpesaPhoneNumber
    ): TaskResult<PaymentResult> {
        try {

            val timeStamp = getCurrentTimestamp()

            val password = createEncodedPassword(
                shortCode = shortCode.value,
                passKey = passKey.value,
                timeStamp = timeStamp
            )

            val result = mpesaRemoteDataSource.lipaNaMpesa(
                authRequest = authRequest,
                request = LipaNaMpesaRequest(
                    accountReference = account.value,
                    amount = amount.value.toInt().toString(),
                    businessShortCode = shortCode.value,
                    callBackURL = callBackUrl.toString(),
                    partyA = phoneNumber.value,
                    partyB = shortCode.value,
                    password = password,
                    phoneNumber = phoneNumber.value,
                    timestamp = timeStamp,
                    transactionDesc = description.value,
                    transactionType = "CustomerPayBillOnline"
                )
            )

            return when (result) {

                is TaskResult.FailedResult<LipaNaMpesaResponse> -> TaskResult.FailedResult(
                    result.failure
                )

                is TaskResult.SuccessResult<LipaNaMpesaResponse> -> {
                    TaskResult.SuccessResult(
                        data = PaymentResult(
                            transactionId = result.data.checkoutRequestId,
                            status = PaymentStatus.PENDING,
                            message = result.data.customerMessage
                        ),
                        message = result.message
                    )
                }

            }

        } catch (e: Exception) {
            return TaskResult.FailedResult(Failure(exception = e, reason = e.message))
        }
    }

    private fun getCurrentTimestamp(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        val timestamp = Timestamp(System.currentTimeMillis())
        return simpleDateFormat.format(timestamp)
    }

    private fun createEncodedPassword(
        shortCode: String,
        passKey: String,
        timeStamp: String = getCurrentTimestamp()
    ): String {
        val password = "$shortCode$passKey$timeStamp"
        return Base64.encodeToString(
            password.toByteArray(),
            Base64.NO_WRAP
        )
    }

}