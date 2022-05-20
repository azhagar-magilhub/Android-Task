package com.az.interviewtask.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.az.interviewtask.data.model.NewsModel

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    suspend fun getAll(): List<NewsModel>

    @Insert
    suspend fun insertAll(news: List<NewsModel>)

    @Delete
    suspend fun delete(news: NewsModel)

    @Query("SELECT * from news where title like  :desc")
    suspend fun getSearchResults(desc : String) :List<NewsModel>
}