package com.az.interviewtask

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.az.interviewtask.adapter.NewsListAdapter
import com.az.interviewtask.data.model.NewsModel
import com.az.interviewtask.utils.ViewModelFactory
import com.az.interviewtask.utils.Status
import com.az.interviewtask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var viewModel: MainActivityViewModel
    lateinit var recyclerAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.e("liv",viewModel.topStories.toString())

                    if(newText!!.isNotEmpty()){
//                        list.clear()
                        val search = newText.toLowerCase()
//                        countries.forEach{
//                            if(it.toLowerCase().contains(search)){
//                                list.add(it)
//                            }
//                        }
                        viewModel.getNews(search)
                        recyclerAdapter.notifyDataSetChanged()
                    }else{
//                        list.clear()
//                        list.addAll(countries)
                        viewModel.getNews(newText)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupUI() {
        binding.newsListRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = NewsListAdapter(arrayListOf())
        binding.swipRefreshLayout.setOnRefreshListener {
            binding.swipRefreshLayout.isRefreshing=true
            viewModel.fetchNews()
        }
        binding.newsListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.newsListRecyclerView.context,
                (binding.newsListRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.newsListRecyclerView.adapter = recyclerAdapter

    }

    private fun setupObserver() {
        viewModel.getNews("").observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { news -> renderList(news) }
                    binding.newsListRecyclerView.visibility = View.VISIBLE
                    binding.swipRefreshLayout.isRefreshing=false
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.newsListRecyclerView.visibility = View.GONE
                    binding.swipRefreshLayout.isRefreshing=true
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    binding.swipRefreshLayout.isRefreshing=false
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(news: List<NewsModel>) {
        recyclerAdapter.addData(news)
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        val viewModelFactory = ViewModelFactory( )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

}