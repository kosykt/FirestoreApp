package com.example.firestoreapp.data.firestoredatabase

import com.example.firestoreapp.data.FireStoreDataSource
import com.example.firestoreapp.data.firestoredatabase.model.FireStoreData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FireStoreDataSourceImpl(
    private val db: FirebaseFirestore,
) : FireStoreDataSource {

    override suspend fun getAllData(): QuerySnapshot? {
        return withContext(Dispatchers.IO) {
            db.collection("Data")
                .get()
                .await()
        }
    }

    override suspend fun saveData(data: FireStoreData) {
        withContext(Dispatchers.IO) {
            val transferData = hashMapOf(
                "date" to data.date.timeInMillis,
                "pressure" to data.pressure,
                "pulse" to data.pulse.toString()
            )
            db.collection("Data")
                .add(transferData)
        }
    }
}