package com.example.shift.db

import androidx.room.TypeConverter
import com.example.shift.Id
import com.example.shift.Location
import com.example.shift.Name
import com.example.shift.Picture
import com.squareup.moshi.Moshi

class Converters {
    private val moshi = Moshi.Builder().build()

    private val nameAdapter = moshi.adapter(Name::class.java)
    private val locationAdapter = moshi.adapter(Location::class.java)
    private val idAdapter = moshi.adapter(Id::class.java)
    private val pictureAdapter = moshi.adapter(Picture::class.java)

    @TypeConverter
    fun fromName(value: Name): String = nameAdapter.toJson(value)

    @TypeConverter
    fun toName(value: String): Name? = nameAdapter.fromJson(value)

    @TypeConverter
    fun fromLocation(value: Location): String = locationAdapter.toJson(value)

    @TypeConverter
    fun toLocation(value: String): Location? = locationAdapter.fromJson(value)

    @TypeConverter
    fun fromId(value: Id): String = idAdapter.toJson(value)

    @TypeConverter
    fun toId(value: String): Id? = idAdapter.fromJson(value)

    @TypeConverter
    fun fromPicture(value: Picture): String = pictureAdapter.toJson(value)

    @TypeConverter
    fun toPicture(value: String): Picture? = pictureAdapter.fromJson(value)
}