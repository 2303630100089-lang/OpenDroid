package com.opendroid.shizustore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.opendroid.shizustore.ui.navigation.ShizuStoreNavHost
import com.opendroid.shizustore.ui.theme.ShizuStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val container = (application as ShizuStoreApplication).appContainer
        setContent {
            val repository = remember { container.repository }
            val installerManager = remember { container.installerManager }
            ShizuStoreTheme {
                ShizuStoreNavHost(repository = repository, installerManager = installerManager)
            }
        }
    }
}
