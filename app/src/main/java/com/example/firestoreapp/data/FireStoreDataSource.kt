package com.example.firestoreapp.data

import com.example.firestoreapp.data.firestoredatabase.model.FireStoreData

interface FireStoreDataSource {

    suspend fun getAllData(): List<FireStoreData>
    suspend fun saveData(data: FireStoreData)
}