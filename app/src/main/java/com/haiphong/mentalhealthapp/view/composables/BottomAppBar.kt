package com.haiphong.mentalhealthapp.view.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.haiphong.mentalhealthapp.Route

sealed class Screen(
    val route: String,
    val imgVector: ImageVector,
) {
    object Home : Screen(route = Route.Home.name, imgVector = Icons.Default.Home)
    object BookAppointment : Screen(route = Route.BookAppointment.name, imgVector = Icons.Default.Call)
    object Journal :
        Screen(route = Route.JournalList.name, imgVector = Icons.Default.List)

    object Profile : Screen(route = Route.Profile.name, imgVector = Icons.Default.Person)
}

val screens = listOf(
    Screen.Home,
    Screen.BookAppointment,
    Screen.Journal,
    Screen.Profile
)

fun reroute(route: String): String {
    return when (route) {
        Route.Home.name -> "content"
        Route.BookAppointment.name -> "bookAppointment"
        Route.JournalList.name -> "journal"
        Route.Profile.name -> "customerProfile"
        else -> ""
    }

}

@Composable
fun BottomAppBar(navController: NavController) {
    BottomNavigation(backgroundColor = Color.White) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        screen.imgVector,
                        contentDescription = null
                    )
                },
                label = { Text(text = screen.route) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(reroute(screen.route)) {
                        popUpTo(Route.Home.name) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}