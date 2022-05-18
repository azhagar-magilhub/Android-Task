package com.az.interviewtask

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
import com.az.interviewtask.retrofit.HackerNewsApi
import com.az.interviewtask.retrofit.HackerNewsApiService


class MainActivityViewModel(
) : ViewModel() {
    private val api: HackerNewsApiService = HackerNewsApi.retrofitService

    var topStoriesIds: List<String> by mutableStateOf(listOf())
    var topStories: List<NewsModel> by mutableStateOf(listOf())
    var topStoriesFilter: List<NewsModel> by mutableStateOf(listOf())


    private var start: Int = 0
    private var end: Int = 20

    private val news = MutableLiveData<Resource<List<NewsModel>>>()


    init {
        fetchNews()
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
            } catch (e: Exception) {
                Log.d("ViewModel:Fetch Stories", e.toString())
            }
        }
    }

    fun getNews(key: String): LiveData<Resource<List<NewsModel>>> {

        if(key!!.isNotEmpty()){
            val list: MutableList<NewsModel> = topStories.toMutableList()

            val filteredList: List<NewsModel> = list.filter {
                it.title.toLowerCase().contains(key)
            }
            Log.e("filteredList",filteredList.toString())
            news.postValue(Resource.success(filteredList))
        }else{
            news.postValue(Resource.success(topStories))
        }

        Log.e("topStories",topStories.toString())


        return news
    }

}



