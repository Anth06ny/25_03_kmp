package org.example.project.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.example.project.MainViewModel
import org.example.project.model.Photographer
import org.jetbrains.compose.resources.painterResource


@Composable
fun PhotographersScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel { MainViewModel() }
) {
    val list by mainViewModel.dataList.collectAsStateWithLifecycle()
    val runInProgress by mainViewModel.runInProgress.collectAsStateWithLifecycle()
    val errorMessage by mainViewModel.errorMessage.collectAsStateWithLifecycle()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        AnimatedVisibility(visible = errorMessage.isNotBlank()) {
            Text(
                color = MaterialTheme.colorScheme.onErrorContainer,
                text = errorMessage,
                modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer).fillMaxWidth()
            )
        }

        AnimatedVisibility(visible = runInProgress) {
            CircularProgressIndicator()
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list.size) {
                PhotographerItem(list[it])
            }
        }

    }

}

@Composable
fun PhotographerItem(photographer: Photographer, onItemClick: (Photographer) -> Unit = {}) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(photographer) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {


        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {


            AsyncImage(
                model = photographer.photoUrl,
                contentDescription = photographer.stageName,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.compose_multiplatform),
                error = painterResource(Res.drawable.compose_multiplatform),
                onError = { println(it) },
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = photographer.stageName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = photographer.story,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Voir les photos",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}