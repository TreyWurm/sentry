package com.example.paginationscratchapp.data.model

data class Profile(
    val firstName: String? = "",
    val pictureUrl: String? = "",
    val learns: List<String?>? = mutableListOf(),
    val referenceCnt: Int? = 0,
    val natives: List<String?>? = mutableListOf(),
    val topic: String? = ""
)