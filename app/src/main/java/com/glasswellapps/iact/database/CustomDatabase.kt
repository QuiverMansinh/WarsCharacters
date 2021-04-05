package com.glasswellapps.iact.database

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CustomData::class), version = 1)
abstract class CustomDatabase : RoomDatabase() {
    abstract fun getCustomDAO(): CustomDAO

    companion object{
        private var instance: CustomDatabase? = null

        fun getInstance(context: Context): CustomDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CustomDatabase::class.java,
                    "custom_characters"
                ).allowMainThreadQueries().build()
            }
            return instance
        }
    }
}

