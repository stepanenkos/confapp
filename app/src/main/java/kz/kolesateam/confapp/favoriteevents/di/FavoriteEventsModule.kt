package kz.kolesateam.confapp.favoriteevents.di

import kz.kolesateam.confapp.favoriteevents.data.DefaultFavoritesRepository
import kz.kolesateam.confapp.favoriteevents.domain.FavoriteEventActionObservable
import kz.kolesateam.confapp.favoriteevents.domain.FavoritesRepository
import kz.kolesateam.confapp.favoriteevents.presentation.FavoriteEventsViewModel
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
            objectMapper = get(),
            eventApiDataMapper = get(),
            favoriteEventActionObservable = get()
        )
    }

    single<FavoriteEventActionObservable> {
        FavoriteEventActionObservable()
    }
}