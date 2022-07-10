package com.example.firestoreapp.data

import com.example.firestoreapp.data.firestoredatabase.model.FireStoreData
import kotlinx.coroutines.flow.Flow

interface FireStoreDataSource {

    fun getAllData(): Flow<List<FireStoreData>>
    suspend fun saveData(data: FireStoreData)
}