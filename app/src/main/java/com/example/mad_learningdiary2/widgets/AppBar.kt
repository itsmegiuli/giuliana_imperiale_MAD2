package com.example.mad_learningdiary2.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mad_learningdiary2.models.Movie

@Composable
fun SimpleAppBar(title: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigateUp() },//navigates back
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
    )
}
