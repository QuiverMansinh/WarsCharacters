package com.glasswellapps.iact.database

import androidx.room.*

@Dao
interface CustomDAO {
    @Query("SELECT * FROM customdata")
    fun getAll(): List<CustomData>

    @Query("SELECT * FROM customdata WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<CustomData>


    @Query("SELECT * FROM customdata WHERE fileName LIKE :fileName AND characterName LIKE " +
            ":characterName")
    fun loadAllByName(fileName:String,characterName:String): List<CustomData>



    @Insert
    fun insertAll(vararg fileName: CustomData)

    @Insert
    fun insert(fileName: CustomData):Long


    @Query("DELETE FROM customdata WHERE id LIKE :deleteId")
    fun deleteById(deleteId:Int)

    @Update
    fun update(character: CustomData)

    @Query("SELECT id FROM customdata WHERE rowid = :rowId")
    fun getPrimaryKey(rowId:Long):Int

}