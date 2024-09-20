package net.thebookofcode.www.amlweather.logic.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observer(): Flow<Status>

    enum class Status{
        Available,Unavailable,Lost,Losing
    }
}