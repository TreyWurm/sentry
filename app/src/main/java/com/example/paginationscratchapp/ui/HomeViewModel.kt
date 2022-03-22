package com.example.paginationscratchapp.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paginationscratchapp.data.model.Profile
import com.example.paginationscratchapp.domain.GetCommunityUseCase
import com.example.paginationscratchapp.util.ExceptionParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetCommunityUseCase) : ViewModel() {

    private var _communityState = MutableStateFlow<State>(State.LOADING(showLoadingMain = true, showLoadingFooter = null, isLoading = false))
    val communityState = _communityState.asStateFlow()

    private var _communityShared = MutableSharedFlow<State>()
    val communityShared = _communityShared.asSharedFlow()

    private var currentPage = 1
    private val totalPage = 3

    fun loadFirstPage() {
        viewModelScope.launch {
            try {
                val profiles = useCase.loadData(currentPage)
                _communityState.value = State.SUCCESS(profiles.toMutableList())
                if (currentPage < totalPage && profiles.isNotEmpty()) {
                    _communityState.value = State.LOADING(showLoadingMain = false, showLoadingFooter = true, isLoading = false)
                } else {
                    _communityState.value = State.LASTPAGE(true)
                }
            } catch (e: Exception) {
                _communityShared.emit(State.ERROR(ExceptionParser.getMessage(e)))
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _communityState.value = State.LOADING(showLoadingMain = false, showLoadingFooter = null, isLoading = true)
            currentPage += 1
            try {
                val profiles = useCase.loadData(currentPage)
                _communityState.value = State.LOADING(showLoadingMain = false, showLoadingFooter = false, isLoading = false)
                _communityState.value = State.SUCCESS(profiles.toMutableList())

                if (currentPage != totalPage && profiles.isNotEmpty()) {
                    _communityState.value = State.LOADING(showLoadingMain = false, showLoadingFooter = true, isLoading = false)
                } else {
                    _communityState.value = State.LASTPAGE(true)
                }
            } catch (e: Exception) {
                _communityShared.emit(State.ERROR(ExceptionParser.getMessage(e)))
            }
        }
    }

    sealed class State {
        data class SUCCESS(val data: MutableList<Profile>) : State()
        data class ERROR(@StringRes val message: Int) : State()
        data class LOADING(val showLoadingMain: Boolean, val showLoadingFooter: Boolean?, val isLoading: Boolean) : State()
        data class LASTPAGE(val isLastPage : Boolean):State()
    }
}