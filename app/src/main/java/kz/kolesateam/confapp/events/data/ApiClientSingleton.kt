package kz.kolesateam.confapp.events.data

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiClientSingleton {
    fun <T> getApiClient(baseUrl: String, factory: Converter.Factory, clazz: Class<T>): T {
        val apiRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(factory)
            .build()
        return apiRetrofit.create(clazz)
    }
}

