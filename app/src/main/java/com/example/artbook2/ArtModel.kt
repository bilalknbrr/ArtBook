package com.example.artbook2

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class ArtModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var imageUri: String,
    var artistName: String,
    var artName: String,
    var year: String) {
}