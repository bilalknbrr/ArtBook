package com.example.artbook2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtModel::class], version = 1)
abstract class ArtDataBase : RoomDatabase() {
    abstract fun artDao(): ArtDao
}


