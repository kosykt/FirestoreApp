package com.example.firestoreapp.data

import com.example.firestoreapp.domain.DataSourceRepository
import com.example.firestoreapp.domain.model.DomainData
import com.example.firestoreapp.domain.model.UseCaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DataSourceRepositoryImpl(
    private val fireStoreDataSource: FireStoreDataSource,
) : DataSourceRepository {
    override fun getAllData(): Flow<UseCaseResponse> {
        return try {
            fireStoreDataSource.getAllData().map {
                UseCaseResponse.Success(it.toListDomainData())
            }
        } catch (e: Exception) {
            flow { emit(UseCaseResponse.Error(e.message.toString())) }
        }
    }

    override suspend fun saveData(data: DomainData) {
        fireStoreDataSource.saveData(data.toFireStoreData())
    }
}