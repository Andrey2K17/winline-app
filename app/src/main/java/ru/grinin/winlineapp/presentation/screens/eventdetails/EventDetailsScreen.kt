package ru.grinin.winlineapp.presentation.screens.eventdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.grinin.winlineapp.R
import ru.grinin.winlineapp.presentation.components.ErrorScreen
import ru.grinin.winlineapp.presentation.components.LoadingState
import ru.grinin.winlineapp.presentation.models.EventDetailsVO
import ru.grinin.winlineapp.presentation.models.MarketDetailsVO
import ru.grinin.winlineapp.presentation.models.OddsChangeDirection
import ru.grinin.winlineapp.presentation.models.OutcomeDetailsVO
import ru.grinin.winlineapp.presentation.screens.events.EventTopBar
import ru.grinin.winlineapp.presentation.state.EventDetailsUiState
import ru.grinin.winlineapp.presentation.viewmodel.EventDetailsViewModel

@Composable
fun EventDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EventDetailsViewModel = koinViewModel<EventDetailsViewModel>()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                is EventDetailsUiState.Error -> {
                    ErrorScreen(
                        message = state.message,
                        onRetry = viewModel::refresh
                    )
                }

                is EventDetailsUiState.Loading -> {
                    LoadingState()
                }

                is EventDetailsUiState.Success -> {
                    EventDetailsContent(state.event)
                }
            }
        }
    }
}

@Composable
fun EventDetailsContent(
    event: EventDetailsVO
) {
    val groupedMarkets = remember(event.markets) {
        event.markets.groupBy { it.id }
            .entries
            .sortedBy { it.key }
    }
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (event.markets.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.markets),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(groupedMarkets.size) { index ->
                val (marketId, markets) = groupedMarkets[index]
                MarketGroup(
                    marketId = marketId,
                    markets = markets,
                    defaultExpanded = index < 10,
                )
            }
        }
    }
}

@Composable
private fun MarketGroup(
    marketId: Int,
    markets: List<MarketDetailsVO>,
    defaultExpanded: Boolean = true,
) {

    var isExpanded by remember { mutableStateOf(defaultExpanded) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpanded = !isExpanded
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = marketId.toString(),
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandMore else Icons.Default.ExpandLess,
                    contentDescription = null,
                    tint = Color.Gray,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(isExpanded) {
                Column {
                    markets.forEach { market ->
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            market.outcomes.forEach { outcome ->
                                OutcomeCard(
                                    outcome = outcome,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OutcomeCard(
    outcome: OutcomeDetailsVO,
    modifier: Modifier = Modifier
) {
    val isActive = outcome.active
    val isBlinking = outcome.isBlinking
    val changeDirection = outcome.changeDirection

    val indicatorAlpha by animateFloatAsState(
        targetValue = if (isBlinking) 1f else 0f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(durationMillis = 400)
        ),
        label = stringResource(R.string.indicator)
    )

    Box(
        modifier = modifier
            .height(64.dp)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${outcome.id}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) Color(0xFFFF6B00) else Color.Gray
            )
            Text(
                text = outcome.odds.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) Color.Black else Color.Gray
            )
        }

        when {
            isBlinking && changeDirection == OddsChangeDirection.UP -> {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(Color(0xFF4CAF50).copy(alpha = indicatorAlpha))
                )
            }
            isBlinking && changeDirection == OddsChangeDirection.DOWN -> {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(Color(0xFFF44336).copy(alpha = indicatorAlpha))
                )
            }
        }
    }
}