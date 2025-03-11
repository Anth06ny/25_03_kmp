package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.ui.screens.PhotographersScreen
import org.example.project.ui.theme.AppTheme

@Preview
@Composable
fun ScreensPreviews() {
    AppTheme {
    val viewModel = MainViewModel()
        viewModel.loadFakeData(true, "Une erreur")

        PhotographersScreen(mainViewModel =  viewModel)
    }
}