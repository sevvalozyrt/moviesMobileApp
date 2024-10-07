package com.example.movies.util

import android.content.Context
import android.content.SharedPreferences

class SpecialSharedPreferences {

    companion object {

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile
        private var instance: SpecialSharedPreferences? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createSpecialSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createSpecialSharedPreferences(context: Context) : SpecialSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }
    fun zamaniKaydet(zaman : Long) {
        sharedPreferences?.edit()?.putLong(TIME, zaman)?.apply()
    }

    fun zamaniAl() = sharedPreferences?.getLong(TIME, 0)
}