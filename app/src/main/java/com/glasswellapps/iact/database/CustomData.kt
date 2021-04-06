package com.glasswellapps.iact.database

import androidx.room.*

//TODO storeRandom, settings, customImage

@Entity
data class CustomData (
    @ColumnInfo(name = "fileName") var fileName: String?,
    @ColumnInfo(name = "characterName") val characterName: String?,

    @ColumnInfo(name = "health") val health: Int,
    @ColumnInfo(name = "endurance") val endurance: Int,
    @ColumnInfo(name = "speed") val speed: Int,
    @ColumnInfo(name = "defence") val defence: String,

    @ColumnInfo(name = "strength") val strength: String,
    @ColumnInfo(name = "insight") val insight: String,
    @ColumnInfo(name = "tech") val tech: String,

    @ColumnInfo(name = "strengthWounded") val strengthWounded: String,
    @ColumnInfo(name = "insightWounded") val insightWounded: String,
    @ColumnInfo(name = "techWounded") val techWounded: String,

    @ColumnInfo(name = "xpEndurance1") val xpEndurance1: Int,
    @ColumnInfo(name = "xpEndurance2") val xpEndurance2: Int,
    @ColumnInfo(name = "xpEndurance3") val xpEndurance3: Int,
    @ColumnInfo(name = "xpEndurance4") val xpEndurance4: Int,
    @ColumnInfo(name = "xpEndurance5") val xpEndurance5: Int,
    @ColumnInfo(name = "xpEndurance6") val xpEndurance6: Int,
    @ColumnInfo(name = "xpEndurance7") val xpEndurance7: Int,
    @ColumnInfo(name = "xpEndurance8") val xpEndurance8: Int,

    @ColumnInfo(name = "xpHealth1") val xpHealth1: Int,
    @ColumnInfo(name = "xpHealth2") val xpHealth2: Int,
    @ColumnInfo(name = "xpHealth3") val xpHealth3: Int,
    @ColumnInfo(name = "xpHealth4") val xpHealth4: Int,
    @ColumnInfo(name = "xpHealth5") val xpHealth5: Int,
    @ColumnInfo(name = "xpHealth6") val xpHealth6: Int,
    @ColumnInfo(name = "xpHealth7") val xpHealth7: Int,
    @ColumnInfo(name = "xpHealth8") val xpHealth8: Int,

    @ColumnInfo(name = "xpSpeed1") val xpSpeed1: Int,
    @ColumnInfo(name = "xpSpeed2") val xpSpeed2: Int,
    @ColumnInfo(name = "xpSpeed3") val xpSpeed3: Int,
    @ColumnInfo(name = "xpSpeed4") val xpSpeed4: Int,
    @ColumnInfo(name = "xpSpeed5") val xpSpeed5: Int,
    @ColumnInfo(name = "xpSpeed6") val xpSpeed6: Int,
    @ColumnInfo(name = "xpSpeed7") val xpSpeed7: Int,
    @ColumnInfo(name = "xpSpeed8") val xpSpeed8: Int,

    @ColumnInfo(name = "bio_title") val bio_title: String,
    @ColumnInfo(name = "bio_quote") val bio_quote: String,
    @ColumnInfo(name = "bio_text") val bio_text: String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}