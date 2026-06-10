package ru.grinin.winlineapp.presentation.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel
import ru.grinin.winlineapp.R
import ru.grinin.winlineapp.presentation.components.ErrorScreen
import ru.grinin.winlineapp.presentation.components.LoadingFooter
import ru.grinin.winlineapp.presentation.components.LoadingState
import ru.grinin.winlineapp.presentation.state.EventVO
import ru.grinin.winlineapp.presentation.state.EventsUiState
import ru.grinin.winlineapp.presentation.viewmodel.EventsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventsViewModel = koinViewModel<EventsViewModel>()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val blinkingState by viewModel.blinkingState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { EventTopBar(onRefresh = viewModel::refresh) }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(paddingValues),
        ) {
            when (val state = uiState) {
                is EventsUiState.Error -> {
                    ErrorScreen(
                        message = state.message,
                        onRetry = viewModel::refresh
                    )
                }

                EventsUiState.Loading -> {
                    LoadingState()
                }

                is EventsUiState.Success -> {
                    EventsListContent(
                        pagingData = state.events,
                        blinkingEventIds = blinkingState.blinkingEventIds
                    )
                }
            }
        }
    }
}

@Composable
fun EventsListContent(
    pagingData: Flow<PagingData<EventVO>>,
    blinkingEventIds: Set<Long>,
) {
    val pagingItems = pagingData.collectAsLazyPagingItems()

    when {
        pagingItems.loadState.refresh is LoadState.Loading -> {
            LoadingState()
        }

        pagingItems.loadState.refresh is LoadState.Error -> {
            ErrorScreen(
                message = stringResource(R.string.failed_to_load_events),
                onRetry = { pagingItems.refresh() }
            )
        }

        else -> {
            EventList(
                pagingItems = pagingItems,
                blinkingEventIds = blinkingEventIds
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTopBar(onRefresh: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.live_events)) },
        actions = {
            IconButton(onClick = onRefresh) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            }
        }
    )
}

@Composable
fun EventList(
    pagingItems: LazyPagingItems<EventVO>,
    blinkingEventIds: Set<Long>,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = { index -> pagingItems[index]?.id ?: index }
        ) { index ->
            pagingItems[index]?.let { event ->
                EventCard(
                    event = event,
                    isBlinking = blinkingEventIds.contains(event.id)
                )
            }
        }

        if (pagingItems.loadState.append is LoadState.Loading) {
            item {
                LoadingFooter()
            }
        }
    }
}

@Composable
fun EventCard(
    event: EventVO,
    isBlinking: Boolean,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isBlinking) Color.Green else Color.White,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = stringResource(R.string.blink_animation)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            TeamNameText(name = event.homeTeamName)
            Spacer(modifier = Modifier.height(4.dp))
            TeamNameText(name = event.awayTeamName)
            Spacer(modifier = Modifier.height(4.dp))
            TeamNameText(name = event.fullPath)
        }
    }
}

@Composable
fun TeamNameText(name: String) {
    Text(
        text = name,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
}
