package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.events.data.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.events.domain.UpcomingEventsRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private val BASE_URL = "http://37.143.8.68:2020"

val eventScreenModule: Module = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get(),
            userNameDataSource = get(named(SHARED_PREFS_DATA_SOURCE))
        ) as UpcomingEventsRepository
    }

}