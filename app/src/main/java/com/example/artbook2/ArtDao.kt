package com.example.artbook2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDao {

    @Query("SELECT * FROM arts")
    fun getAllArts(): Flow<List<ArtModel>>

    @Insert
    suspend fun insertArt(art: ArtModel)

    @Delete
    suspend fun deleteArt(art: ArtModel)

}