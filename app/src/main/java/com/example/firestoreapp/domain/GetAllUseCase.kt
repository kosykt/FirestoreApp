package com.example.firestoreapp.domain

import com.example.firestoreapp.domain.model.UseCaseResponse
import kotlinx.coroutines.flow.Flow

class GetAllUseCase(
    private val repository: DataSourceRepository
) {
    fun execute(): Flow<UseCaseResponse> = repository.getAllData()
}