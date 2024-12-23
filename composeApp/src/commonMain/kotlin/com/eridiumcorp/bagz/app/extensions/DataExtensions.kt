package com.eridiumcorp.bagz.app.extensions

fun Map<String, Any?>.extractDouble(key: String): Double? {
    val value = this[key]
    return when (value) {
        is Double -> value
        is Int -> value.toDouble()
        is Float -> value.toDouble()
        else -> null
    }
}

fun Map<String, Any?>.extractString(key: String): String? {
    val value = this[key]
    return when (value) {
        is String -> value
        else -> null
    }
}

fun Map<String, Any?>.extractBoolean(key: String): Boolean? {
    val value = this[key]
    return value as? Boolean
}

fun Map<String, Any?>.extractStringList(key: String): List<String>? {
    val value = this[key]
    return when (value) {
        is List<*> -> value.filterIsInstance<String>()
        else -> null
    }
}

fun Map<String, Any?>.extractMap(key: String): Map<String, Any?>? {
    val value = this[key]
    if (value is Map<*, *> && value.keys.all { it is String }) {
        @Suppress("UNCHECKED_CAST")
        return value as Map<String, Any?>
    }
    return null
}

fun Map<String, Any?>.extractMapList(key: String): List<Map<String, Any?>>? {
    val value = this[key]
    if (value is List<*>) {
        return value.mapNotNull { item ->
            if (item is Map<*, *> && item.keys.all { it is String }) {
                @Suppress("UNCHECKED_CAST")
                item as Map<String, Any?>
            } else {
                null
            }
        }
    }
    return null
}