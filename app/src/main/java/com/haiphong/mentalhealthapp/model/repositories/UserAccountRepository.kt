package com.haiphong.mentalhealthapp.model.repositories

interface UserAccountRepository {
    fun createUserAccount()
    fun changePassword()
    fun changeProfile()
}