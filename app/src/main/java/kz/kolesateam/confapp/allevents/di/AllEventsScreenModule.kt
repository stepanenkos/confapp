package kz.kolesateam.confapp.allevents.di

import kz.kolesateam.confapp.allevents.data.DefaultAllEventsRepository
import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.domain.AllEventsRepository
import kz.kolesateam.confapp.allevents.presentation.AllEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val allEventsScreenModule: Module = module {
    viewModel {
        AllEventsViewModel(
            allEventsRepository = get(),
            favoriteEventsRepository = get()
        )
    }

    single() {
        val retrofit: Retrofit = get()

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory<AllEventsRepository> {
        DefaultAllEventsRepository(
            allEventsDataSource = get()
        )
    }
}