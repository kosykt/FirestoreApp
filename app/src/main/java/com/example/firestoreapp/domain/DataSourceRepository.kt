package com.example.firestoreapp.domain

import com.example.firestoreapp.domain.model.DomainData
import com.example.firestoreapp.domain.model.UseCaseResponse
import kotlinx.coroutines.flow.Flow

interface DataSourceRepository {

    suspend fun getAllData(): UseCaseResponse
    suspend fun saveData(data: DomainData)
}