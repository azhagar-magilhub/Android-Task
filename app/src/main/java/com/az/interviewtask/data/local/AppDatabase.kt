package com.az.interviewtask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.az.interviewtask.data.local.dao.UserDao
import com.az.interviewtask.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}