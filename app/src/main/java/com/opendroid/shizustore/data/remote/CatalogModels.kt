package com.opendroid.shizustore.data.remote

data class CatalogResponse(
    val generatedAt: String,
    val apps: List<CatalogApp>
)

data class CatalogApp(
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
