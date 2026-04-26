package com.example.lnm.core.common.base_use_case

import com.example.lnm.core.common.result.TaskResult

interface BaseUseCase<I, O> {
    suspend fun execute(input: I): TaskResult<O>
}