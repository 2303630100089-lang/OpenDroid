package com.opendroid.shizustore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM apps ORDER BY name ASC")
    fun observeAll(): Flow<List<AppEntity>>

    @Query("SELECT * FROM apps WHERE packageName = :packageName LIMIT 1")
    fun observeByPackageName(packageName: String): Flow<AppEntity?>

    @Query("SELECT * FROM apps WHERE name LIKE '%' || :query || '%' OR summary LIKE '%' || :query || '%' ORDER BY name ASC")
    fun search(query: String): Flow<List<AppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(apps: List<AppEntity>)

    @Query("SELECT COUNT(*) FROM apps")
    suspend fun count(): Int
}
