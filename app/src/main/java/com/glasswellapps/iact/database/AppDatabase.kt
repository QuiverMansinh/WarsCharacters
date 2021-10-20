package com.glasswellapps.iact.database

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(version = 2, entities = arrayOf(CharacterData::class,CustomData::class))
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCharacterDAO(): CharacterDAO
    abstract fun getCustomDAO(): CustomDAO
    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "save_files"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}

