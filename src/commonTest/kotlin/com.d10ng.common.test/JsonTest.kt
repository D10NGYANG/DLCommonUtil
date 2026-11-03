package com.d10ng.common.test

import com.d10ng.common.transform.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonTest {
    @Serializable
    private data class Settings(val name: String = "default", val enabled: Boolean = true)

    @Test
    fun preservesConfiguredDefaultsAndWireFormat() {
        val decoded = json.decodeFromString<Settings>(
            """{"name":"中文😀","enabled":null,"unknown":42}""",
        )
        assertEquals(Settings("中文😀"), decoded)
        assertEquals("""{"name":"中文😀","enabled":true}""", json.encodeToString(decoded))
        assertEquals(Settings("plain"), json.decodeFromString<Settings>("""{name:plain}"""))
    }
}
