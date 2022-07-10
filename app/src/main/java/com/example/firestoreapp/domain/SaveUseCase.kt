package com.example.firestoreapp.domain

import com.example.firestoreapp.domain.model.DomainData

class SaveUseCase(
    private val repository: DataSourceRepository
) {
    suspend fun execute(data: DomainData): Unit = repository.saveData(data)
}