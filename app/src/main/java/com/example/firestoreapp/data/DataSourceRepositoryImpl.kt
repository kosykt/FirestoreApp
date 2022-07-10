package com.example.firestoreapp.data

import com.example.firestoreapp.domain.DataSourceRepository
import com.example.firestoreapp.domain.model.DomainData
import com.example.firestoreapp.domain.model.UseCaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataSourceRepositoryImpl(
    private val fireStoreDataSource: FireStoreDataSource
) : DataSourceRepository {
    override suspend fun getAllData(): Flow<UseCaseResponse> {
        return try {
            val data: List<DomainData> = fireStoreDataSource.getAllData().toListDomainData()
            flow { emit(UseCaseResponse.Success(data)) }
        } catch (e: Exception) {
            flow { emit(UseCaseResponse.Error(e.message.toString())) }
        }
    }

    override suspend fun saveData(data: DomainData) {
        fireStoreDataSource.saveData(data.toFireStoreData())
    }
}