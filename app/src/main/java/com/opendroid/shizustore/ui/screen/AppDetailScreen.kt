package com.opendroid.shizustore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.opendroid.shizustore.data.repository.AppCatalogRepository

@Composable
fun AppDetailScreen(
    contentPadding: PaddingValues,
    repository: AppCatalogRepository,
    packageName: String
) {
    val app by repository.observeApp(packageName).collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(app?.name ?: "Loading...", style = MaterialTheme.typography.headlineSmall)
        Text(app?.description.orEmpty(), style = MaterialTheme.typography.bodyMedium)
        Text("Version: ${app?.latestVersion.orEmpty()}")
        Text("Source: ${app?.source.orEmpty()}")
        Text("Package: ${app?.packageName.orEmpty()}")
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
            Text("Download")
        }
    }
}
