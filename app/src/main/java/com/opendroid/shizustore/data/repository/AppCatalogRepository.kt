package com.opendroid.shizustore.data.repository

import com.opendroid.shizustore.data.local.AppDao
import com.opendroid.shizustore.data.local.AppEntity
import com.opendroid.shizustore.data.remote.NetworkCatalogSource
import com.opendroid.shizustore.domain.AppListing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppCatalogRepository(
    private val dao: AppDao,
    private val source: NetworkCatalogSource
) {
    fun observeCatalog(): Flow<List<AppListing>> = dao.observeAll().map { list ->
        list.map(AppEntity::toDomain)
    }

    fun observeApp(packageName: String): Flow<AppListing?> = dao.observeByPackageName(packageName).map { it?.toDomain() }

    fun search(query: String): Flow<List<AppListing>> = dao.search(query).map { list -> list.map(AppEntity::toDomain) }

    suspend fun syncCatalog() {
        val remote = source.fetchCatalog()
        dao.upsertAll(
            remote.apps.map {
                AppEntity(
                    packageName = it.packageName,
                    name = it.name,
                    summary = it.summary,
                    description = it.description,
                    iconUrl = it.iconUrl,
                    categoriesCsv = it.categories.joinToString(","),
                    source = it.source,
                    latestVersion = it.latestVersion,
                    downloadUrl = it.downloadUrl,
                    projectUrl = it.projectUrl,
                    supportsShizuku = it.supportsShizuku,
                    supportsRoot = it.supportsRoot,
                    isBeta = it.isBeta,
                    updatedAt = System.currentTimeMillis()
                )
            }
        )
    }

    suspend fun hasCatalog() = dao.count() > 0
}

private fun AppEntity.toDomain(): AppListing = AppListing(
    packageName = packageName,
    name = name,
    summary = summary,
    description = description,
    iconUrl = iconUrl,
    categories = categoriesCsv.split(',').filter { it.isNotBlank() },
    source = source,
    latestVersion = latestVersion,
    downloadUrl = downloadUrl,
    projectUrl = projectUrl,
    supportsShizuku = supportsShizuku,
    supportsRoot = supportsRoot,
    isBeta = isBeta
)
