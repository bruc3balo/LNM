package com.example.lnm.payments.domain.value_objects

import java.math.BigDecimal

@JvmInline
value class Money(val value: BigDecimal) {

    init {
        require(value >= BigDecimal.ZERO) { "Money cannot be negative" }
        require(value.scale() <= 2) { "Money cannot have more than 2 decimal places" }
    }

    operator fun plus(other: Money): Money = Money(this.value + other.value)
    operator fun minus(other: Money): Money = Money(this.value - other.value)

}