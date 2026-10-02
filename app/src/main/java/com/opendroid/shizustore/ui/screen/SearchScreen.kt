package com.opendroid.shizustore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.opendroid.shizustore.data.repository.AppCatalogRepository

@Composable
fun SearchScreen(
    contentPadding: PaddingValues,
    repository: AppCatalogRepository,
    onOpenApp: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val apps by repository.search(query).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            label = { Text("Search apps") },
            singleLine = true
        )
        if (query.isBlank()) {
            Text("Type an app name or package to search", style = MaterialTheme.typography.bodyMedium)
        } else if (apps.isEmpty()) {
            Text("No apps found for \"$query\"", style = MaterialTheme.typography.bodyMedium)
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(apps) { app ->
                AppRow(name = app.name, summary = app.summary, onClick = { onOpenApp(app.packageName) })
            }
        }
    }
}
