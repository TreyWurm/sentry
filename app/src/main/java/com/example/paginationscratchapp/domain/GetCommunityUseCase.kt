package com.example.paginationscratchapp.domain

import com.example.paginationscratchapp.data.api.CommunityService
import com.example.paginationscratchapp.data.model.Profile
import com.example.paginationscratchapp.di.IoDispatcher
import com.example.paginationscratchapp.domain.CommunityMapper.toProfiles
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCommunityUseCase {
    suspend fun loadData(pageNumber: Int): List<Profile>
}

class GetCommunityUseCaseImpl @Inject constructor(private val service: CommunityService, @IoDispatcher private val dispatcher: CoroutineDispatcher) : GetCommunityUseCase {

    override suspend fun loadData(pageNumber: Int): List<Profile> {
        val result = withContext(dispatcher) {
            service.getCommunity(pageNumber)
        }
        return result.response.toProfiles()
    }
}