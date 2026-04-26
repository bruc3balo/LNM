package com.example.lnm.core.common.result

import com.example.lnm.core.common.failure.Failure

sealed class TaskResult<T> {

    data class SuccessResult<T>(
        val data: T,
        val message: String
    ) : TaskResult<T>()

    data class FailedResult<T>(
        val failure: Failure,
    ) : TaskResult<T>()

}



