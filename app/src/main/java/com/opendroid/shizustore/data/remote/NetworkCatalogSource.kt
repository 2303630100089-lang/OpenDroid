package com.opendroid.shizustore.data.remote

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

class NetworkCatalogSource(
    private val api: CatalogApi,
    private val context: Context
) {
    suspend fun fetchCatalog(): CatalogResponse {
        return runCatching { api.getCatalogFromRepo() }
            .getOrElse { loadAssetFallback() }
    }

    private fun loadAssetFallback(): CatalogResponse {
        context.assets.open("catalog.json").use { input ->
            InputStreamReader(input).use { reader ->
                return Gson().fromJson(reader, CatalogResponse::class.java)
            }
        }
    }
}
