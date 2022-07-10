package com.example.firestoreapp.domain.model

sealed class UseCaseResponse {
    data class Success<out T: List<DomainData>>(val data: T) : UseCaseResponse()
    data class Error(val message: String) : UseCaseResponse()
}
