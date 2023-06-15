package com.example.backend.PostgreSQL.Transactions

import com.example.backend.DataClasses.PlaceTime.PlaceTimeDTO
import java.time.Instant

interface PlaceTimeTransaction : CRUDOperations<PlaceTimeDTO, Long> {
    suspend fun createPlaceTime(placeTime : PlaceTimeDTO) : PlaceTimeDTO?

    suspend fun selectByPlace(placeId : Long) : List<PlaceTimeDTO>

    suspend fun updatePlaceTime(placeTime : PlaceTimeDTO) : PlaceTimeDTO?

    suspend fun selectIdByDate(date : Instant) : List<PlaceTimeDTO>

    suspend fun selectDateById(placeTimeId : Long) : Instant?

    suspend fun delete(id : Long) : Boolean
}