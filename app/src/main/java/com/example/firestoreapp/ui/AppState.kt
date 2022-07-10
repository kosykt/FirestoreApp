package com.example.firestoreapp.ui

import com.example.firestoreapp.domain.model.DomainData

sealed class AppState {
    data class Success<out T: List<DomainData>>(val data: T) : AppState()
    data class Error(val error: String) : AppState()
    object Loading : AppState()
}
