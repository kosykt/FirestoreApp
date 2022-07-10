package com.example.firestoreapp.ui.pulseandpressure

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firestoreapp.data.DataSourceRepositoryImpl
import com.example.firestoreapp.data.firestoredatabase.FireStoreDataSourceImpl
import com.example.firestoreapp.domain.GetAllUseCase
import com.example.firestoreapp.domain.SaveUseCase
import com.example.firestoreapp.domain.model.DomainData
import com.example.firestoreapp.domain.model.UseCaseResponse
import com.example.firestoreapp.ui.AppState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

private const val COROUTINE_EXCEPTION_HANDLER = "COROUTINE_EXCEPTION_HANDLER"

class PulseAndPressureViewModel : ViewModel() {

    private val fireStoreDataSource = FireStoreDataSourceImpl()
    private val repository = DataSourceRepositoryImpl(fireStoreDataSource)
    private val getAllUseCase = GetAllUseCase(repository)
    private val saveUseCase = SaveUseCase(repository)

    private val coroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, throwable ->
            mutableStateFlow.value = AppState.Error(throwable.message.toString())
            Log.e(COROUTINE_EXCEPTION_HANDLER, throwable.message.toString())
        }
    private val baseViewModelScope = viewModelScope.plus(coroutineContext)

    private val mutableStateFlow: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)
    val stateFlow: StateFlow<AppState> = mutableStateFlow.asStateFlow()

    fun saveData(model: DomainData) {
        baseViewModelScope.launch {
            saveUseCase.execute(model)
        }
    }

    fun getData() {
        baseViewModelScope.launch {
            try {
                getAllUseCase.execute().collectLatest { response ->
                    when (response) {
                        is UseCaseResponse.Error -> {
                            mutableStateFlow.value = AppState.Error(response.message)
                        }
                        is UseCaseResponse.Success<*> -> {
                            mutableStateFlow.value = AppState.Success((response.data))
                        }
                    }
                }
            } catch (e: Exception) {
                mutableStateFlow.value = AppState.Error(e.message.toString())
            }
        }
    }
}