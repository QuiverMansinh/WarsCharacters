package com.example.imperialassault

import androidx.room.*

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM characterdata")
    fun getAll(): List<CharacterData>

    @Query("SELECT * FROM characterdata WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<CharacterData>


    @Query("SELECT * FROM characterdata WHERE fileName LIKE :fileName AND characterName LIKE :characterName")
    fun loadAllByName(fileName:String,characterName:String): List<CharacterData>



    @Insert
    fun insertAll(vararg fileName: CharacterData)

    @Insert
    fun insert(fileName: CharacterData):Long

    @Delete
    fun delete(character: CharacterData)

    @Update
    fun update(character: CharacterData)
}