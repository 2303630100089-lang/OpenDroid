package com.opendroid.shizustore.data.remote

import retrofit2.http.GET

interface CatalogApi {
    @GET("2303630100089-lang/OpenDroid/main/app/src/main/assets/catalog.json")
    suspend fun getCatalogFromRepo(): CatalogResponse
}
