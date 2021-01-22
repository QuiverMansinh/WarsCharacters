package com.example.imperialassault

import android.content.Context
import androidx.room.*
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CharacterData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCharacterDAO(): CharacterDAO

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance(context: Context):AppDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "save_files"
                ).allowMainThreadQueries().build()
            }
            return instance
        }
    }
}

