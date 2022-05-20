package com.az.interviewtask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class NewsModel(
    @PrimaryKey val id: Int,
    val title: String?,
    val by: String?,
    val score: String?,
    val text: String?,
    val time: String?,
    val type: String?,
    val url: String?,
)
