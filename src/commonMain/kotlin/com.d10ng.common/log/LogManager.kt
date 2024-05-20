package com.d10ng.common.log

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

/**
 * 日志管理器
 */
object LogManager {

    private val scope = CoroutineScope(Dispatchers.Default)
    // 日志数据Flow，时间戳 to 日志内容
    val dataFlow = MutableSharedFlow<Pair<Long, String>>()

    /**
     * 提交日志
     * @param message String
     */
    fun emit(message: String) {
        scope.launch {
            dataFlow.emit(Clock.System.now().toEpochMilliseconds() to message)
        }
    }
}