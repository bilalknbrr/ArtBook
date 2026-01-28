package com.example.artbook2

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class ArtModel(
    var imageUri: String,
    var artistName: String,
    var artName: String,
    var year: String)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}