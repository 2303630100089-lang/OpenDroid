package com.opendroid.shizustore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class AppEntity(
    @PrimaryKey val packageName: String,
    val name: String,
    val summary: String,
    val description: String,
    val iconUrl: String,
    val categoriesCsv: String,
    val source: String,
    val latestVersion: String,
    val downloadUrl: String,
    val projectUrl: String,
    val supportsShizuku: Boolean,
    val supportsRoot: Boolean,
    val isBeta: Boolean,
    val updatedAt: Long
)
