package com.example.shift

import com.example.shift.db.UserEntity
import com.squareup.moshi.JsonClass

data class RandomUserResponse(
    val results: List<User>
)

data class User(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String,
)

@JsonClass(generateAdapter = true)
data class Name(
    val title: String,
    val first: String,
    val last: String,
)

@JsonClass(generateAdapter = true)
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone,
)

@JsonClass(generateAdapter = true)
data class Street(
    val number: Long,
    val name: String,
)

@JsonClass(generateAdapter = true)
data class Coordinates(
    val latitude: String,
    val longitude: String,
)

@JsonClass(generateAdapter = true)
data class Timezone(
    val offset: String,
    val description: String,
)

@JsonClass(generateAdapter = true)
data class Dob(
    val date: String,
    val age: Long,
)

@JsonClass(generateAdapter = true)
data class Registered(
    val date: String,
    val age: Long,
)

@JsonClass(generateAdapter = true)
data class Id(
    val name: String,
    val value: String?,
)

@JsonClass(generateAdapter = true)
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String,
)

fun User.toEntity(): UserEntity = UserEntity(
    email = email,
    gender = gender,
    name = name,
    location = location,
    dobDate = dob.date,
    dobAge = dob.age,
    registeredDate = registered.date,
    registeredAge = registered.age,
    phone = phone,
    cell = cell,
    id = id,
    picture = picture,
    nat = nat
)

fun UserEntity.toUser(): User = User(
    gender = gender,
    name = name,
    location = location,
    email = email,
    dob = Dob(dobDate, dobAge),
    registered = Registered(registeredDate, registeredAge),
    phone = phone,
    cell = cell,
    id = id,
    picture = picture,
    nat = nat
)
