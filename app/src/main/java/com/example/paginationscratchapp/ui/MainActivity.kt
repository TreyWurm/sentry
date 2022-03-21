package com.example.paginationscratchapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paginationscratchapp.R
import com.example.paginationscratchapp.ui.HomeViewModel.State
import com.example.paginationscratchapp.data.api.CommunityService
import com.example.paginationscratchapp.util.CustomOnScrollListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: PaginationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var isLoading = false
    private var isLastPage = false
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var communityService: CommunityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initObservable()
    }
    private fun initView() {
        mAdapter = PaginationAdapter()
        recyclerView = findViewById(R.id.rv_list)
        progressBar = findViewById(R.id.pb_main)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            addOnScrollListener(object : CustomOnScrollListener(linearLayoutManager) {
                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun loadMoreItems() {
                    viewModel.loadNextPage()
                }
            })
        }
    }

    private fun initObservable() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.communityShared.collect {
                    if (it is State.ERROR){
                        Toast.makeText(this@MainActivity,it.error.message, Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                        Timber.e("Error Message = ${it.error.message}")
                        isLoading = false
                    }
                }
            }

        }
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.communityState.collectLatest{
                    when (it) {
                        is State.SUCCESS -> mAdapter.setData(it.data)

                        is State.LOADING -> {
                            isLoading = it.isLoading
                            if (it.showLoadingMain){
                                progressBar.visibility = View.VISIBLE
                            }else{
                                progressBar.visibility = View.GONE
                            }
                            it.showLoadingFooter?.let { isLoadingFooterShown ->
                                if (isLoadingFooterShown) {
                                    mAdapter.addLoadingFooter()
                                } else {
                                    mAdapter.removeLoadingFooter()
                                }
                            }
                        }
                        is State.LASTPAGE -> {
                            isLastPage = it.isLastPage
                            progressBar.visibility = View.GONE
                            mAdapter.notifyDataSet()
                        }

                        is State.ERROR ->{
                        }
                    }
                }
            }
        }
        viewModel.loadFirstPage()
    }
}