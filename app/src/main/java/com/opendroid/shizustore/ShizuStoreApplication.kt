package com.opendroid.shizustore

import android.app.Application
import androidx.room.Room
import com.opendroid.shizustore.data.local.ShizuStoreDatabase
import com.opendroid.shizustore.data.remote.CatalogApi
import com.opendroid.shizustore.data.remote.NetworkCatalogSource
import com.opendroid.shizustore.data.repository.AppCatalogRepository
import com.opendroid.shizustore.installer.InstallerManager
import com.opendroid.shizustore.sync.CatalogSyncScheduler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShizuStoreApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
        CatalogSyncScheduler.schedule(this)
    }
}

class AppContainer(application: Application) {
    private val database: ShizuStoreDatabase = Room.databaseBuilder(
        application,
        ShizuStoreDatabase::class.java,
        "shizustore.db"
    ).fallbackToDestructiveMigration().build()

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .client(OkHttpClient.Builder().addInterceptor(logging).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val catalogApi: CatalogApi = retrofit.create(CatalogApi::class.java)

    val repository: AppCatalogRepository = AppCatalogRepository(
        dao = database.appDao(),
        source = NetworkCatalogSource(catalogApi, application)
    )

    val installerManager: InstallerManager = InstallerManager(application)
}
