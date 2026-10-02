package com.opendroid.shizustore.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.opendroid.shizustore.ShizuStoreApplication

class CatalogSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as ShizuStoreApplication
        return runCatching {
            app.appContainer.repository.syncCatalog()
            Result.success()
        }.getOrElse {
            Result.retry()
        }
    }
}
