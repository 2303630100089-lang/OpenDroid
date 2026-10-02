package com.opendroid.shizustore.domain

data class AppListing(
    val packageName: String,
    val name: String,
    val summary: String,
    val description: String,
    val iconUrl: String,
    val categories: List<String>,
    val source: String,
    val latestVersion: String,
    val downloadUrl: String,
    val projectUrl: String,
    val supportsShizuku: Boolean,
    val supportsRoot: Boolean,
    val isBeta: Boolean
)

enum class InstallerType {
    SHIZUKU,
    ROOT,
    SESSION,
    SYSTEM,
    CUSTOM
}

data class InstallerState(
    val shizukuAvailable: Boolean,
    val rootAvailable: Boolean,
    val selected: InstallerType
)
