package com.brayo.greenhaven.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.navigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.width(300.dp)
            ) {
                DrawerContent(navController, drawerState, scope)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            "GreenHaven",
                            style = MaterialTheme.typography.titleLarge
                        ) 
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            HomeContent(navController, paddingValues)
        }
    }
}

@Composable
private fun HomeContent(
    navController: NavController,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Quick Actions Section
        Text(
            "Quick Actions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionCard(
                icon = Icons.Default.AddCircle,
                title = "Add Product",
                onClick = { navController.navigate(ROUT_ADD_PRODUCT) }
            )
            ActionCard(
                icon = Icons.Default.List,
                title = "View Products",
                onClick = { navController.navigate(ROUT_PRODUCT_LIST) }
            )
        }
    }
}

@Composable
private fun ActionCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 12.dp)
    ) {
        DrawerHeader()
        Divider()
        DrawerBody(navController, drawerState, scope)
    }
}

@Composable
private fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "GreenHaven",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun DrawerBody(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val items = listOf(
        Pair("Home", Icons.Default.Home),
        Pair("Products", Icons.Default.List),
        Pair("Add Product", Icons.Default.AddCircle),
        Pair("About", Icons.Default.Info),
        Pair("Login", Icons.Default.Person),
        Pair("Register", Icons.Default.PersonAdd)
    )

    Column {
        items.forEach { (title, icon) ->
            NavigationDrawerItem(
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(title) },
                selected = false,
                onClick = {
                    val route = when(title) {
                        "Home" -> ROUT_HOME
                        "Products" -> ROUT_PRODUCT_LIST
                        "Add Product" -> ROUT_ADD_PRODUCT
                        "About" -> ROUT_ABOUT
                        "Login" -> ROUT_LOGIN
                        "Register" -> ROUT_REGISTER
                        else -> ROUT_HOME
                    }
                    navController.navigate(route)
                    scope.launch { drawerState.close() }
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}