package com.example.freeweights

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        val currentRoute = currentRoute(navController)
        AnimatedIconButton(onClick = {navController.navigate("profile")},
            isSelected = currentRoute == "profile",
            icon = if (currentRoute == "profile") Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
            contentDescription = "profile",
            modifier = Modifier.weight(1f),
            )
        AnimatedIconButton(onClick = {navController.navigate("exercise")},
            isSelected = currentRoute == "exercise",
            icon = if (currentRoute == "exercise") Icons.Filled.Create else Icons.Outlined.Create,
            contentDescription = "exercise",
            modifier = Modifier.weight(1f),
            )
        AnimatedIconButton(onClick = {navController.navigate("schedule")},
            isSelected = currentRoute == "schedule",
            icon = if (currentRoute == "schedule") Icons.Filled.DateRange else Icons.Outlined.DateRange,
            contentDescription = "schedule",
            modifier = Modifier.weight(1f),
            )
        AnimatedIconButton(onClick = {navController.navigate("workout")},
            isSelected = currentRoute == "workout",
            icon = if (currentRoute == "workout") Icons.Filled.Menu else Icons.Outlined.Menu,
            contentDescription = "workout",
            modifier = Modifier.weight(1f),
            )
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun AnimatedIconButton(
    onClick: () -> Unit,
    isSelected: Boolean,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.9f else 1.6f, label = "")

        IconButton(
            onClick = onClick,
            modifier = Modifier
                .scale(scale)
                .then(modifier)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            Log.d("IconButton", "Pressed")
                            tryAwaitRelease()
                            isPressed = false
                        }
                    )
                }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = if (isSelected) Color.Black else Color.Gray
            )
        }

}
