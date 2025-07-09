package com.example.shift

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift.db.SHIFTDatabase
import com.example.shift.db.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application = application) {

    private val userDao: UserDao by lazy {
        SHIFTDatabase.getDb(application).userDao()
    }

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users


    var selectedUser: User? = null

    init {
        loadUsersFromDatabase()
    }

    private fun loadUsersFromDatabase() {
        viewModelScope.launch {
            if (isDatabaseEmpty()) {
                fetchAndSaveUsers()
            } else {
                observeUsersFromDatabase()
            }
        }
    }

    fun refreshUsers() {
        viewModelScope.launch {
            val newUsers = fetchUsersFromApi()
            newUsers?.let {
                _users.value = it
                userDao.deleteAllUsers()
                userDao.insertUsers(it.map { user ->
                    user.toEntity()
                })
            }
        }
    }

    private fun observeUsersFromDatabase() {
        viewModelScope.launch {
            userDao.getAllUsersFlow()
                .map { userEntities ->
                    userEntities.map { entity -> entity.toUser() }
                }
                .collect { userList ->
                    _users.value = userList
                }
        }
    }

    private suspend fun fetchAndSaveUsers() {
        val usersFromApi = fetchUsersFromApi()
        usersFromApi?.let {
            _users.value = it
            userDao.insertUsers(it.map { user ->
                user.toEntity()
            })
        }
    }

    private suspend fun fetchUsersFromApi(): List<User>? {
        return try {
            val response = RetrofitClient.randomUserAPIService.getRandomUsers(15)
            response.results
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //TODO: заменить в будущем запрос на выгрузку первого пользователя, ведь если он будет выбран, то значит данные в БД существуют
    private suspend fun isDatabaseEmpty(): Boolean {
        return userDao.getUsersCount() == 0
    }

    fun clearDatabase() {
        viewModelScope.launch {
            userDao.deleteAllUsers()
        }
    }
}