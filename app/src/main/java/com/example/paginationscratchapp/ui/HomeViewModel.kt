package com.example.paginationscratchapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paginationscratchapp.HomeUseCase
import com.example.paginationscratchapp.data.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase) : ViewModel() {

    private var _communityObservable = MutableLiveData<State>()
    val communityObservable = _communityObservable as LiveData<State>
    private var _isLastPage = MutableLiveData<Boolean>()
    val isLastPage = _isLastPage as LiveData<Boolean>

    private var currentPage = 1
    private val totalPage = 3

    fun loadFirstPage() {
        viewModelScope.launch {
            val profiles = useCase.loadData(currentPage)
            try {
                _communityObservable.value = State.SUCCESS(profiles?.toMutableList() ?: mutableListOf())
                if (currentPage < totalPage) {
                    _communityObservable.value = State.LOADING(showLoadingMain = false, showLoadingFooter = true, isLoading = false)
                } else {
                    _communityObservable.value = State.LASTPAGE(true)
                }
            } catch (e: Exception) {
                _communityObservable.value = State.ERROR(e)
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _communityObservable.value = State.LOADING(showLoadingMain = false, showLoadingFooter = null, isLoading = true)
            currentPage += 1
            val profiles = useCase.loadData(currentPage)
            try {
                _communityObservable.value = State.LOADING(showLoadingMain = false, showLoadingFooter = false, isLoading = false)
                _communityObservable.value = State.SUCCESS(profiles?.toMutableList() ?: mutableListOf())
                if (currentPage != totalPage) {
                    _communityObservable.value = State.LOADING(showLoadingMain = false, showLoadingFooter = true, isLoading = false)
                } else {
                    _communityObservable.value = State.LASTPAGE(true)
                }
            } catch (e: Exception) {
                _communityObservable.value = State.ERROR(e)
            }
        }
    }

    sealed class State {
        data class SUCCESS(val data: MutableList<Profile>) : State()
        data class ERROR(val error: Throwable) : State()
        data class LOADING(val showLoadingMain: Boolean, val showLoadingFooter: Boolean?, val isLoading: Boolean) : State()
        data class LASTPAGE(val isLastPage : Boolean):State()
    }
}