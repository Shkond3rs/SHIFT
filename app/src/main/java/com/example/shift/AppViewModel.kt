package com.example.shift

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shift.db.SHIFTDatabase
import com.example.shift.db.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application = application) {

    private val userDao: UserDao by lazy {
        SHIFTDatabase.getDb(application).userDao()
    }

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    var selectedUser: User? = null

    init {
        loadUsersFromDatabase()
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

    fun clearError() {
        _errorMessage.value = null
    }

    private fun loadUsersFromDatabase() {
        viewModelScope.launch {
            try {
                if (isDatabaseEmpty()) {
                    fetchAndSaveUsers()
                } else {
                    observeUsersFromDatabase()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки из базы: ${e.localizedMessage ?: "Неизвестная ошибка"}"
            }
        }
    }

    private fun observeUsersFromDatabase() {
        viewModelScope.launch {
            userDao.getAllUsersFlow()
                .map { userEntities ->
                    userEntities.map { entity -> entity.toUser() }
                }
                .catch { e ->
                    _errorMessage.value = "Ошибка чтения из базы: ${e.localizedMessage ?: "Неизвестная ошибка"}"
                }
                .collect { userList ->
                    _users.value = userList
                }
        }
    }

    private suspend fun fetchAndSaveUsers() {
        try {
            val usersFromApi = fetchUsersFromApi()
            if (usersFromApi != null) {
                _users.value = usersFromApi
                userDao.insertUsers(usersFromApi.map { user -> user.toEntity() })
            } else {
                // Если fetchUsersFromApi вернул null, значит там уже установлено сообщение об ошибке
                // Можно добавить дополнительную обработку, если нужно
            }
        } catch (e: Exception) {
            _errorMessage.value = "Ошибка сохранения пользователей: ${e.localizedMessage ?: "Неизвестная ошибка"}"
        }
    }

    private suspend fun fetchUsersFromApi(): List<User>? {
        return try {
            val response = RetrofitClient.randomUserAPIService.getRandomUsers(15)
            response.results
        } catch (e: Exception) {
            _errorMessage.value = "Ошибка при загрузке пользователей: ${e.localizedMessage ?: "Неизвестная ошибка"}"
            null
        }
    }

    private suspend fun isDatabaseEmpty(): Boolean {
        return userDao.getUsersCount() == 0
    }

}