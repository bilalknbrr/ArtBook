package com.example.artbook2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ArtViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        ArtDataBase::class.java,
        "art_database"
    ).build()

    private val dao = db.artDao()

    val artlist : Flow<List<ArtModel>> = dao.getAllArts()

    fun addArt(art: ArtModel){
        viewModelScope.launch {
            dao.insertArt(art)
        }
    }

    fun deleteArt(art: ArtModel){
        viewModelScope.launch {
            dao.deleteArt(art)
        }
    }

}