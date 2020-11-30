package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.DefaultAllEventsRepository
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.events.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.events.domain.AllEventsRepository
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.events.presentation.AllEventsViewModel
import kz.kolesateam.confapp.events.presentation.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private val BASE_URL = "http://37.143.8.68:2020"

const val UPCOMING_EVENTS_VIEW_MODEL = "upcoming_events_view_model"
const val ALL_EVENTS_VIEW_MODEL = "all_events_view_model"

val eventScreenModule: Module = module {

    viewModel(named(UPCOMING_EVENTS_VIEW_MODEL)) {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
        )
    }

    viewModel(named(ALL_EVENTS_VIEW_MODEL)) {
        AllEventsViewModel(
            allEventsRepository = get()
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single() {
        val retrofit: Retrofit = get()

        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    single() {
        val retrofit: Retrofit = get()

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get(),
            userNameDataSource = get()
        ) as UpcomingEventsRepository
    }

    factory {
        DefaultAllEventsRepository(
            allEventsDataSource = get()
        ) as AllEventsRepository
    }

}