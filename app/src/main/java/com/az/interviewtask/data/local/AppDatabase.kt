package com.az.interviewtask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.az.interviewtask.data.local.dao.NewsDao
import com.az.interviewtask.data.model.NewsModel

@Database(entities = [NewsModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

}