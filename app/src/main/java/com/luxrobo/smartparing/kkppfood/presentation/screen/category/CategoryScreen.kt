package com.luxrobo.smartparing.kkppfood.presentation.screen.category

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onBack: () -> Unit,
    onOpenDetail: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("TODO: Category list UI (next commit)")
        }
    }
}
