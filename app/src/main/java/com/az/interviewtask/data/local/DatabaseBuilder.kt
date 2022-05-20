package com.az.interviewtask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.az.interviewtask.data.local.dao.NewsDao
import com.az.interviewtask.data.model.NewsModel

@Database(entities = arrayOf(NewsModel::class), version = 1, exportSchema = false)
abstract class RoomSingleton : RoomDatabase(){
    abstract fun newsDao():NewsDao

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context:Context): RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "interviewtask")
                    .build()
            }

            return INSTANCE as RoomSingleton
        }
    }
}
