package com.example.edward.rxkotlinapp.model

/**
 * Created by Edward on 1/10/2019.
 */



data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String
)
