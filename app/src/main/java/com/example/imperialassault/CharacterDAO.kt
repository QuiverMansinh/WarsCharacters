package com.example.imperialassault

import androidx.room.*

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM characterdata")
    fun getAll(): List<CharacterData>

    @Query("SELECT * FROM characterdata WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CharacterData>

    @Query("SELECT * FROM characterdata WHERE fileName LIKE :fileName")
    fun findByName(fileName:String): CharacterData

    @Insert
    fun insertAll(vararg fileName: CharacterData)

    @Delete
    fun delete(user: CharacterData)
}