package com.example.shift.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class SHIFTDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object DatabaseProvider {
        @Volatile
        private var INSTANCE: SHIFTDatabase? = null

        fun getDb(context: Context): SHIFTDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, SHIFTDatabase::class.java, "shift_database")
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}