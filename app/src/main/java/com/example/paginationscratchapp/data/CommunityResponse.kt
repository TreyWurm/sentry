package com.example.paginationscratchapp.data

import com.google.gson.annotations.SerializedName

data class CommunityResponse(
    @SerializedName("response")
    val response: List<ResponseItem?>? = null,
    @SerializedName("errorCode")
    val errorCode: Any? = null,
    @SerializedName("type")
    val type: String? = null
)

data class ResponseItem(
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("referenceCnt")
    val referenceCnt: Int? = null,
    @SerializedName("pictureUrl")
    val pictureUrl: String? = null,
    @SerializedName("learns")
    val learns: List<String?>? = null,
    @SerializedName("topic")
    val topic: String? = null,
    @SerializedName("natives")
    val natives: List<String?>? = null
)
