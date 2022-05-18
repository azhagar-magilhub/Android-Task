package com.az.interviewtask.data.local

import com.az.interviewtask.data.local.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

}