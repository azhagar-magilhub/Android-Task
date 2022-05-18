package com.az.interviewtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.az.interviewtask.data.model.NewsModel
import com.az.interviewtask.databinding.NewsListRowBinding

class NewsListAdapter(private val news: ArrayList<NewsModel>) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: NewsListRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun addData(list: List<NewsModel>) {
        news.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(news[position]){
                binding.textview.text = this.title
            }
        }
    }

    override fun getItemCount(): Int = news.size

}