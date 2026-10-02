package com.opendroid.shizustore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.opendroid.shizustore.data.repository.AppCatalogRepository
import com.opendroid.shizustore.domain.AppListing
import com.opendroid.shizustore.installer.InstallerManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    contentPadding: PaddingValues,
    repository: AppCatalogRepository,
    installerManager: InstallerManager,
    onOpenApp: (String) -> Unit
) {
    val apps by repository.observeCatalog().collectAsState(initial = emptyList())
    val installerState by installerManager.state.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            if (!repository.hasCatalog()) repository.syncCatalog()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Text("ShizuStore", style = MaterialTheme.typography.headlineSmall)
        Text(
            text = "Installer: ${installerState.selected} • Shizuku ${if (installerState.shizukuAvailable) "ON" else "OFF"} • Root ${if (installerState.rootAvailable) "ON" else "OFF"}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        if (apps.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Syncing app catalog…", style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(apps) { app ->
                    AppCard(app = app, onClick = { onOpenApp(app.packageName) })
                }
            }
        }
    }
}

@Composable
private fun AppCard(app: AppListing, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = app.iconUrl,
                contentDescription = app.name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(app.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = app.summary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 8.dp)) {
                    AssistChip(onClick = {}, label = { Text(app.source) }, colors = AssistChipDefaults.assistChipColors())
                    if (app.supportsShizuku) AssistChip(onClick = {}, label = { Text("Shizuku") })
                    if (app.supportsRoot) AssistChip(onClick = {}, label = { Text("Root") })
                }
            }
        }
    }
}
