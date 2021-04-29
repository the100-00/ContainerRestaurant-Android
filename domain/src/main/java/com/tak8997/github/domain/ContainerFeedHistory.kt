package com.tak8997.github.domain

data class ContainerFeedHistory(
    val id: Int,
    val image: String,
    val title: String,
    val desc: String,
    val likeCount: String,
    val commentCount: String
)