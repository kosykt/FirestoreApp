package com.example.firestoreapp.data

import com.example.firestoreapp.data.firestoredatabase.model.FireStoreData
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FireStoreDataSource {

    suspend fun getAllData(): QuerySnapshot?
    suspend fun saveData(data: FireStoreData)
}