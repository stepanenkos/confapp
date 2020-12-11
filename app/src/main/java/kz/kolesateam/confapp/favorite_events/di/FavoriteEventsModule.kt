package kz.kolesateam.confapp.favorite_events.di

import kz.kolesateam.confapp.favorite_events.data.DefaultFavoritesRepository
import kz.kolesateam.confapp.favorite_events.domain.FavoritesRepository
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val favoriteEventsModule: Module = module {
    viewModel {
        FavoriteEventsViewModel(
            favoritesRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single<FavoritesRepository> {
        DefaultFavoritesRepository(
            context = androidContext(),
            objectMapper = get()
        )
    }
}