package com.example.lnm.payments.domain.value_objects

@JvmInline
value class MpesaPhoneNumber private constructor(val value: String) {

    companion object {

        fun create(input: String): MpesaPhoneNumber {
            val normalized = input.replace("+", "")
                .replace(" ", "")

            require(normalized.startsWith("254")) {
                "Must be a Kenyan number (254...)"
            }

            require(normalized.length == 12)

            return MpesaPhoneNumber(normalized)

        }

    }
}