package com.opendroid.shizustore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AppEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShizuStoreDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
