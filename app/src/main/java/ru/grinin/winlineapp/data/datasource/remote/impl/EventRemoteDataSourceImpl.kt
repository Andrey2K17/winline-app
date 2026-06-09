package ru.grinin.winlineapp.data.datasource.remote.impl

import android.os.Build
import androidx.annotation.RequiresApi
import ru.grinin.winlineapp.data.datasource.remote.ApiService
import ru.grinin.winlineapp.data.datasource.remote.EventRemoteDataSource
import ru.grinin.winlineapp.data.models.remote.eventsrequest.EventsRequest
import ru.grinin.winlineapp.data.models.remote.eventsrequest.IdFilter
import ru.grinin.winlineapp.data.models.remote.eventsrequest.MarketsFilter
import ru.grinin.winlineapp.data.models.remote.eventsrequest.Query
import ru.grinin.winlineapp.data.models.remote.eventsrequest.Scheduled
import ru.grinin.winlineapp.data.models.remote.eventsrequest.WithQuery
import ru.grinin.winlineapp.data.models.remote.eventsresponse.EventsResponseDto
import java.time.Duration
import java.time.Instant

class EventRemoteDataSourceImpl(
    private val apiService: ApiService,
) : EventRemoteDataSource{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getEvents(): EventsResponseDto {
        val time = Instant.now().plus(Duration.ofDays(1)).toEpochMilli()

        val request = EventsRequest(
            query = Query(
                scheduled = Scheduled(lte = time)
            ),
            with = listOf(
                "markets", "competitors", "match",
                "activeMarketsCount", "match.venue", "t_settings"
            ),
            withQuery = WithQuery(
                markets = MarketsFilter(
                    id = IdFilter(
                        `in` = getMarketIds()
                    )
                )
            )
        )

        return apiService.getEvents(request)
    }

    private fun getMarketIds(): List<Int> = listOf(
        1, 610, 186, 219, 340, 251, 16, 256, 493, 188, 187, 237, 223, 18,
        238, 225, 258, 259, 314, 189, 494, 2, 3, 4, 123, 406, 309349, 309350,
        327, 328, 395, 202, 245, 310100, 310101, 310102, 310103, 310104,
        310105, 310106, 310107, 310108, 310109, 310110, 310111, 310112,
        310113, 310333, 310334, 310335, 310336, 310337, 310338, 310339,
        309812, 309813, 309814, 309815, 309816, 310403, 310404, 310340,
        310341, 310342, 310343, 310344, 310345, 310346, 310023, 310024,
        310025, 310026, 310027, 310555, 310556, 310214, 310215, 310216,
        310217, 310218, 310219, 310220, 310200, 310201, 310202, 310203,
        310204, 310205, 310206, 310428, 310429, 310430, 310431, 310432,
        310433, 310434, 310033, 310034, 310035, 310036, 310037, 310559, 310560
    )
}