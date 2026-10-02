package com.opendroid.shizustore.util

object VersionComparator {
    fun isUpdateAvailable(current: String, latest: String): Boolean {
        val currentParts = current.split(".").mapNotNull { it.toIntOrNull() }
        val latestParts = latest.split(".").mapNotNull { it.toIntOrNull() }
        val max = maxOf(currentParts.size, latestParts.size)

        for (i in 0 until max) {
            val left = currentParts.getOrElse(i) { 0 }
            val right = latestParts.getOrElse(i) { 0 }
            if (right > left) return true
            if (right < left) return false
        }
        return false
    }
}
