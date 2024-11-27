package com.example.movieapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.entity.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){

    abstract fun movieDao(): MovieDao
}