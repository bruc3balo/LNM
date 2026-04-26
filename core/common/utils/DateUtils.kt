package com.example.lnm.core.common.utils

import java.util.Date
import kotlin.time.Duration


operator fun Date.plus(duration: Duration): Date = Date(this.time + duration.inWholeMilliseconds)

fun Date.diffInMs(other: Date): Long = time - other.time