package com.example.paginationscratchapp.domain

import com.example.paginationscratchapp.data.api.CommunityService
import com.example.paginationscratchapp.data.model.Profile
import com.example.paginationscratchapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HomeUseCase {
    suspend fun loadData(pageNumber: Int): List<Profile>?
}

class HomeUseCaseImpl @Inject constructor(private val service: CommunityService, @IoDispatcher private val dispatcher: CoroutineDispatcher) : HomeUseCase {
    override suspend fun loadData(pageNumber: Int): List<Profile>? {
        val result = withContext(dispatcher) {
            service.getCommunity(pageNumber)
        }
        return result.response?.map { CommunityMapper.convertToProfile(it) }
    }
}