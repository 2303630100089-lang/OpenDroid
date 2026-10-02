package com.opendroid.shizustore.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VersionComparatorTest {

    @Test
    fun detectsUpdateWhenLatestIsGreater() {
        assertTrue(VersionComparator.isUpdateAvailable("1.2.0", "1.3.0"))
    }

    @Test
    fun noUpdateWhenEqual() {
        assertFalse(VersionComparator.isUpdateAvailable("2.0.1", "2.0.1"))
    }

    @Test
    fun noUpdateWhenCurrentAhead() {
        assertFalse(VersionComparator.isUpdateAvailable("2.4.0", "2.3.9"))
    }
}
