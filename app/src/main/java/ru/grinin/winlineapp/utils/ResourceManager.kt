package ru.grinin.winlineapp.utils

import android.content.Context
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes id: Int): String
    fun getString(@StringRes id: Int, vararg args: Any): String
}

class ResourceManagerImpl(private val context: Context) : ResourceManager {
    override fun getString(id: Int): String = context.getString(id)
    override fun getString(id: Int, vararg args: Any): String = context.getString(id, *args)
}