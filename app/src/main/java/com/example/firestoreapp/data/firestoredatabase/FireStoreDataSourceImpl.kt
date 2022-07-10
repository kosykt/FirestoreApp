package com.example.firestoreapp.data.firestoredatabase

import com.example.firestoreapp.data.FireStoreDataSource
import com.example.firestoreapp.data.firestoredatabase.model.FireStoreData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*

class FireStoreDataSourceImpl : FireStoreDataSource {

    override fun getAllData(): Flow<List<FireStoreData>> {
        return flow { emit(listData) }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveData(data: FireStoreData) {
        withContext(Dispatchers.IO) { listData.add(data) }
    }

    private val listData = mutableListOf<FireStoreData>(
        FireStoreData(
            date = GregorianCalendar(2022, 0, 1, 12, 0),
            pressure = "120/90",
            pulse = 80
        ),
        FireStoreData(
            date = GregorianCalendar(2022, 0, 1, 12, 10),
            pressure = "120/90",
            pulse = 80
        ),
        FireStoreData(
            date = GregorianCalendar(2022, 0, 1, 12, 20),
            pressure = "120/90",
            pulse = 80
        ),
        FireStoreData(
            date = GregorianCalendar(2022, 0, 1, 12, 30),
            pressure = "120/90",
            pulse = 80
        ),
        FireStoreData(
            date = GregorianCalendar(2022, 1, 1, 12, 30),
            pressure = "120/90",
            pulse = 80
        ),
    )
}