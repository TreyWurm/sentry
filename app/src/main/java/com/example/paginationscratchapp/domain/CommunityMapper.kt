package com.example.paginationscratchapp.domain

import com.example.paginationscratchapp.data.model.Profile
import com.example.paginationscratchapp.data.ResponseItem

object CommunityMapper {
    fun List<ResponseItem?>?.toProfiles(): List<Profile> {
        val profiles = ArrayList<Profile>()
        this?.forEach { item->
            profiles.add(Profile(
                firstName = item?.firstName,
                pictureUrl = item?.pictureUrl,
                learns = item?.learns,
                topic = item?.topic,
                referenceCnt = item?.referenceCnt,
                natives = item?.natives
            ))
        }
        return profiles
    }
}