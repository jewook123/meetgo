package com.meetgo.app.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import com.meetgo.app.ui.components.MeetGoBottomBar
import com.meetgo.app.ui.screens.chat.ChatScreen
import com.meetgo.app.ui.screens.discover.DiscoverScreen
import com.meetgo.app.ui.screens.discover.ProfileDetailScreen
import com.meetgo.app.ui.screens.login.LoginScreen
import com.meetgo.app.ui.screens.login.SignUpScreen
import com.meetgo.app.ui.screens.matches.MatchesScreen
import com.meetgo.app.ui.screens.myprofile.MyProfileScreen
import com.meetgo.app.ui.screens.onboarding.OnboardingScreen
import com.meetgo.app.ui.screens.profile.ProfileSetupScreen

private val bottomBarRoutes = setOf(Screen.Discover.route, Screen.Matches.route, Screen.MyProfile.route)

@Composable
fun MeetGoNavHost(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                MeetGoBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Discover.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(Screen.Onboarding.route) {
                OnboardingScreen(onGetStarted = { navController.navigate(Screen.Login.route) })
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Discover.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                )
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onSignUpSuccess = {
                        navController.navigate(Screen.ProfileSetup.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    },
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable(Screen.ProfileSetup.route) {
                ProfileSetupScreen(
                    onComplete = {
                        navController.navigate(Screen.Discover.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.Discover.route) {
                DiscoverScreen(
                    onProfileClick = { userId ->
                        navController.navigate(Screen.ProfileDetail.createRoute(userId))
                    },
                    onMatched = { otherUserId ->
                        val matchId = "m-$otherUserId"
                        navController.navigate(Screen.Chat.createRoute(matchId))
                    },
                )
            }
            composable(Screen.ProfileDetail.route) { entry ->
                val userId = entry.arguments?.getString(Screen.ProfileDetail.ARG_USER_ID).orEmpty()
                ProfileDetailScreen(
                    userId = userId,
                    onBack = { navController.popBackStack() },
                    onLike = { navController.popBackStack() },
                    onSkip = { navController.popBackStack() },
                )
            }
            composable(Screen.Matches.route) {
                MatchesScreen(
                    onMatchClick = { matchId ->
                        navController.navigate(Screen.Chat.createRoute(matchId))
                    },
                )
            }
            composable(Screen.Chat.route) { entry ->
                val matchId = entry.arguments?.getString(Screen.Chat.ARG_MATCH_ID).orEmpty()
                ChatScreen(
                    matchId = matchId,
                    onBack = { navController.popBackStack() },
                    onUnmatch = { navController.popBackStack() },
                )
            }
            composable(Screen.MyProfile.route) {
                MyProfileScreen(
                    onEditProfile = { navController.navigate(Screen.ProfileSetup.route) },
                    onLogout = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}
