package com.example.firestoreapp.domain

import com.example.firestoreapp.domain.model.UseCaseResponse

class GetAllUseCase(
    private val repository: DataSourceRepository,
) {
    suspend fun execute(): UseCaseResponse = repository.getAllData()
}