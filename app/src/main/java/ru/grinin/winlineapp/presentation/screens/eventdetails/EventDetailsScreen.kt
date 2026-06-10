package ru.grinin.winlineapp.presentation.screens.eventdetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
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

            items(
                items = event.markets,
                key = { it.uniqueKey }
            ) { market ->
                MarketCard(market = market)
            }
        }
    }
}

@Composable
private fun MarketCard(market: MarketDetailsVO) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = buildString {
                    append(stringResource(R.string.market_id, market.id))
                    if (market.specifiers.isNotEmpty()) {
                        append(", ${market.specifiers}")
                    }
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                market.outcomes.forEach { outcome ->
                    OutcomeCard(
                        outcome = outcome,
                        modifier = Modifier.weight(1f)
                    )
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
            .background(Color(0xFFF5F5F5))
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