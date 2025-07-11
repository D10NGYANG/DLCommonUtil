package com.d10ng.common.log

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * 日志管理器
 */
object LogManager {
    // 日志数据Flow，时间戳 to 日志内容
    val dataFlow = MutableSharedFlow<Pair<Long, String>>(
        extraBufferCapacity = 4096,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    /**
     * 提交日志
     * @param message String
     */
    @OptIn(ExperimentalTime::class)
    fun emit(message: String) {
        while (true) {
            if (dataFlow.tryEmit(Clock.System.now().toEpochMilliseconds() to message)) break
        }
    }
}