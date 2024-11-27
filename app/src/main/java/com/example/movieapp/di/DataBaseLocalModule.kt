package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.db.AppDataBase
import com.example.movieapp.util.DBConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DataBaseLocalModule {

    @Provides
    fun providesDataBase(
        @ApplicationContext context: Context
    ) : AppDataBase = Room.databaseBuilder(
        context = context,
        klass = AppDataBase::class.java,
        name = DBConstants.MOVIE_DATABASE
    ).build()

    @Provides
    fun providesMovieDao(
        appDataBase: AppDataBase
    ) : MovieDao = appDataBase.movieDao()

}