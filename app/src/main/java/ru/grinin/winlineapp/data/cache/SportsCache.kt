package ru.grinin.winlineapp.data.cache

import ru.grinin.winlineapp.data.models.remote.sport.CategoryDto
import ru.grinin.winlineapp.data.models.remote.sport.SportDto
import ru.grinin.winlineapp.data.models.remote.sport.TournamentDto

private const val DASH = "-"
private const val DASH_FULL = "-, -, -"

class SportsCache {

    private val sportsMap = mutableMapOf<Int, SportDto>()
    private val categoriesMap = mutableMapOf<Int, CategoryDto>()
    private val tournamentsMap = mutableMapOf<Int, TournamentDto>()

    fun updateCache(tree: List<SportDto>) {
        sportsMap.clear()
        categoriesMap.clear()
        tournamentsMap.clear()

        tree.forEach { sport ->
            sportsMap[sport.id] = sport
            sport.categories?.forEach { category ->
                categoriesMap[category.id] = category
                category.tournaments?.forEach { tournament ->
                    tournamentsMap[tournament.id] = tournament
                }
            }
        }
    }

    fun getFullPath(sportId: Int?, categoryId: Int?, tournamentId: Int?): String {
        return "${getSportName(sportId ?: return DASH_FULL)}, " +
                "${getCategoryName(categoryId ?: return DASH_FULL)}, " +
                getTournamentName(tournamentId ?: return DASH_FULL)
    }

    fun getSportName(sportId: Int): String = sportsMap[sportId]?.name ?: DASH
    fun getCategoryName(categoryId: Int): String = categoriesMap[categoryId]?.name ?: DASH
    fun getTournamentName(tournamentId: Int): String = tournamentsMap[tournamentId]?.name ?: DASH
}