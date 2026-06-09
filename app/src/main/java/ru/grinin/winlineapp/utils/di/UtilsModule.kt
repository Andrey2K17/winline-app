package ru.grinin.winlineapp.utils.di

import org.koin.dsl.module
import ru.grinin.winlineapp.utils.ResourceManager
import ru.grinin.winlineapp.utils.ResourceManagerImpl

val utilsModule = module {
    single<ResourceManager> { ResourceManagerImpl(context = get()) }
}