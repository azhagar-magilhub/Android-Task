package com.az.interviewtask.data.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("by")
    val by: String = "",
    @SerializedName("score")
    val score: String = "",
    @SerializedName("text")
    val text: String = "",
    @SerializedName("time")
    val time: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("url")
    val url: String = "",
)
