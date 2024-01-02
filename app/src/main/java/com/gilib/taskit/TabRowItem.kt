package com.gilib.taskit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabRowItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Starred : TabRowItem(
        title = "Starred",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star
    )

    object Tasks: TabRowItem(
        title = "My Tasks",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List
    )
}
