package com.example.movies.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.model.Movie
import kotlin.concurrent.Volatile

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDAO

    companion object {
        @Volatile
        private var instance : MovieDatabase? = null
        private val lock = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "MovieDatabase"
        ).build()

    }

}