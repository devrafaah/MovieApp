package com.example.movieapp.di

import com.example.movieapp.data.api.ServiceApi
import com.example.movieapp.data.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesServiceProvider() = ServiceProvider()

    @Provides
    fun providerServiceApi(serviceProvider: ServiceProvider) : ServiceApi {
        return serviceProvider.createService(ServiceApi::class.java)
    }
}