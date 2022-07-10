package com.example.firestoreapp.data

import com.example.firestoreapp.domain.DataSourceRepository
import com.example.firestoreapp.domain.model.DomainData
import com.example.firestoreapp.domain.model.UseCaseResponse

class DataSourceRepositoryImpl(
    private val fireStoreDataSource: FireStoreDataSource,
) : DataSourceRepository {
    override suspend fun getAllData(): UseCaseResponse {
        return try {
            UseCaseResponse.Success(fireStoreDataSource.getAllData())
        } catch (e: Exception) {
            UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun saveData(data: DomainData) {
        fireStoreDataSource.saveData(data.toFireStoreData())
    }
}