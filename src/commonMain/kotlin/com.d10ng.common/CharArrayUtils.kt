package com.d10ng.common

/**
 * 将 CharArray 转换成 ByteArray
 * @receiver [CharArray]
 * @return [ByteArray]
 */
fun CharArray.toByteArray(): ByteArray {
    val byteList = mutableListOf<Byte>()
    for (char in this) {
        byteList.add(char.code.toByte())
    }
    return byteList.toByteArray()
}