package com.example.shift

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shift.screens.UserDetailsScreen
import com.example.shift.screens.UserListScreen

object NavRoutes {
    const val USER_LIST = "user_list"
    const val USER_DETAILS = "user_details"
}


@Composable
fun RandomUserNavGraph(
    navController: NavHostController,
    appViewModel: AppViewModel = viewModel(),
) {
    NavHost(navController = navController, startDestination = NavRoutes.USER_LIST) {
        composable(NavRoutes.USER_LIST) {
            UserListScreen(
                viewmodel = appViewModel,
                onUserClick = { user ->
                    //TODO: delete
                    Log.d("USER: ", user.toString())
                    appViewModel.selectedUser = user
                    navController.navigate(NavRoutes.USER_DETAILS)
                }
            )
        }
        composable(NavRoutes.USER_DETAILS) {
            UserDetailsScreen(
                viewmodel = appViewModel
            )
        }
    }
}