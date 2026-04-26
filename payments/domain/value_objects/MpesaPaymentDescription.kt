package com.example.lnm.payments.domain.value_objects

@JvmInline
value class MpesaPaymentDescription (val value: String) {

    init {
        require(value.length in 4..15) {
            "Between 4 to 15 characters"
        }
    }

}