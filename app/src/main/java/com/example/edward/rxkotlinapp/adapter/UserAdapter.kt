package com.example.edward.rxkotlinapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edward.rxkotlinapp.R
import com.example.edward.rxkotlinapp.model.User
import kotlinx.android.synthetic.main.user_item_layout.view.*

/**
 * Created by Edward on 1/10/2019.
 */
class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val userList: MutableList<User> = arrayListOf()

    fun setList(list: List<User>){
        userList.addAll(list)
    }

    fun resetList(){
        userList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        with(holder){
            userFullNameTextView.text = user.name
            userNameTextView.text = user.username
            emailTextView.text = user.email
            suiteTextView.text = user.address.suite
            streetTextView.text = user.address.street
            cityTextView.text = user.address.city
            zipTextView.text = user.address.zipcode
        }
    }

    inner class  UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userFullNameTextView = itemView.textUserFullName
        val userNameTextView = itemView.textUserName
        val emailTextView = itemView.textEmail
        val suiteTextView = itemView.textSuite
        val streetTextView = itemView.textStreet
        val cityTextView = itemView.textCity
        val zipTextView = itemView.textZipcode
    }
}