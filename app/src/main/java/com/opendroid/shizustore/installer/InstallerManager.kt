package com.opendroid.shizustore.installer

import android.content.Context
import com.opendroid.shizustore.domain.InstallerState
import com.opendroid.shizustore.domain.InstallerType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InstallerManager(
    private val context: Context
) {
    private val _state = MutableStateFlow(
        InstallerState(
            shizukuAvailable = false,
            rootAvailable = false,
            selected = InstallerType.SESSION
        )
    )

    val state: StateFlow<InstallerState> = _state

    fun select(type: InstallerType) {
        _state.value = _state.value.copy(selected = type)
    }

    fun refreshEnvironment(shizukuAvailable: Boolean, rootAvailable: Boolean) {
        _state.value = _state.value.copy(
            shizukuAvailable = shizukuAvailable,
            rootAvailable = rootAvailable
        )
    }
}
