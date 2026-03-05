package com.example.app.database

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}