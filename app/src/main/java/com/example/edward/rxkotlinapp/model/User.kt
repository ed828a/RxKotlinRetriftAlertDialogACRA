package com.example.edward.rxkotlinapp.model

/**
 * Created by Edward on 1/10/2019.
 */
data class User(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: Address
)
