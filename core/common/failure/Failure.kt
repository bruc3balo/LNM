package com.example.lnm.core.common.failure

class Failure(
    val type: FailureType = FailureType.UNKNOWN,
    exception: Throwable?,
    reason: String? = type.name
) : Exception(reason, exception)