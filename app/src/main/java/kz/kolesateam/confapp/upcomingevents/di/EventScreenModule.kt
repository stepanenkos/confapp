package kz.kolesateam.confapp.upcomingevents.di

import kz.kolesateam.confapp.upcomingevents.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.upcomingevents.data.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.upcomingevents.domain.UpcomingEventsRepository
import kz.kolesateam.confapp.upcomingevents.presentation.UpcomingEventsViewModel
import kz.kolesateam.confapp.utils.mappers.BranchApiDataMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val eventScreenModule: Module = module {

    viewModel {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),
            favoritesRepository = get(),
            notificationAlarmHelper = get(),
            userNameDataSource = get(),
        )
    }

    single() {
        val retrofit: Retrofit = get()

        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory<UpcomingEventsRepository> {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get(),
            branchApiDataMapper = get()
        )
    }

    factory<BranchApiDataMapper> {
        BranchApiDataMapper()
    }
}