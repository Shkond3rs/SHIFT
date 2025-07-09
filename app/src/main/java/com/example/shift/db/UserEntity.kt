package com.example.shift.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shift.Id
import com.example.shift.Location
import com.example.shift.Name
import com.example.shift.Picture

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val gender: String,
    @ColumnInfo(name = "name") val name: Name,
    @ColumnInfo(name = "location") val location: Location,
    val dobDate: String,
    val dobAge: Long,
    val registeredDate: String,
    val registeredAge: Long,
    val phone: String,
    val cell: String,
    @ColumnInfo(name = "id") val id: Id,
    @ColumnInfo(name = "picture") val picture: Picture,
    val nat: String
)