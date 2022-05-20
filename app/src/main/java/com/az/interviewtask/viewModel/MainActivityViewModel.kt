package com.az.interviewtask

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.az.interviewtask.data.model.NewsModel
import com.az.interviewtask.utils.Resource
import kotlinx.coroutines.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.az.interviewtask.data.local.RoomSingleton
import com.az.interviewtask.retrofit.HackerNewsApi
import com.az.interviewtask.retrofit.HackerNewsApiService

class MainActivityViewModel(application: Application) : ViewModel() {
    private val api: HackerNewsApiService = HackerNewsApi.retrofitService

    private val db: RoomSingleton = RoomSingleton.getInstance(application)

    var topStoriesIds: List<String> by mutableStateOf(listOf())
    var topStories: List<NewsModel> by mutableStateOf(listOf())
    var topStoriesFilter: List<NewsModel> by mutableStateOf(listOf())


    private var start: Int = 0
    private var end: Int = 20

    private val news = MutableLiveData<Resource<List<NewsModel>>>()


    init {
        fetchLocalNews()
        fetchNews()
    }

    fun fetchLocalNews() {
        viewModelScope.launch {
            news.postValue(Resource.loading(null))
            topStoriesFilter = db.newsDao().getAll()
            Log.e("topStoriesFilter", topStoriesFilter.toString())
            news.postValue(Resource.success(topStoriesFilter))
        }
    }

    fun fetchNews() {
        viewModelScope.launch {
            news.postValue(Resource.loading(null))
            try {
                topStoriesIds = api.getTopStories()
                fetchTopStoriesItems()
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
    }

    fun searchForItems(desc: String) : MutableLiveData<Resource<List<NewsModel>>> {
        viewModelScope.launch {
            news.postValue(Resource.loading(null))
            topStoriesFilter = db.newsDao().getSearchResults(desc)
            news.postValue(Resource.success(topStoriesFilter))
        }
        return news
    }


    private fun fetchTopStoriesItems() {
        viewModelScope.launch {
            try {
                for (id in topStoriesIds.subList(start, end)) {
                    val item = api.getItemFromId(id)
                    topStories = topStories + listOf(item)
                    topStoriesFilter = topStoriesFilter + listOf(item)
                }

                Log.e("ViewModel:Fetch Stories", topStories.toString())
                news.postValue(Resource.success(topStories))
                db.newsDao().insertAll(topStories)
            } catch (e: Exception) {
                Log.d("ViewModel:Fetch Stories", e.toString())
            }
        }
    }

    fun getNews(): LiveData<Resource<List<NewsModel>>> {

        return news
    }

}



