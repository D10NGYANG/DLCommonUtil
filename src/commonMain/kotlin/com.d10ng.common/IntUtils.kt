package com.d10ng.common

/**
 * 获取整型数据的 高位 byte
 * @receiver [Int]
 * @return [Byte]
 */
fun Int.takeHighByte(): Byte =
    ushr(8).toByte()

/**
 * 获取整型数据的 低位 byte
 * @receiver [Int]
 * @return [Byte]
 */
fun Int.takeLowByte(): Byte =
    (this and 0xff).toByte()