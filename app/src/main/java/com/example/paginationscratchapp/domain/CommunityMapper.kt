package com.example.paginationscratchapp.domain

import com.example.paginationscratchapp.data.model.Profile
import com.example.paginationscratchapp.data.ResponseItem

object CommunityMapper {
    fun convertToProfile(item: ResponseItem?): Profile {
        return Profile(
            firstName = item?.firstName,
            pictureUrl = item?.pictureUrl,
            learns = item?.learns,
            topic = item?.topic,
            referenceCnt = item?.referenceCnt,
            natives = item?.natives
        )
    }
}