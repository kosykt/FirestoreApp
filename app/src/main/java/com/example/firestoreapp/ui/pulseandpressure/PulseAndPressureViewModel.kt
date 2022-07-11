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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

private const val COROUTINE_EXCEPTION_HANDLER = "COROUTINE_EXCEPTION_HANDLER"

class PulseAndPressureViewModel : ViewModel() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val fireStoreDataSource = FireStoreDataSourceImpl(db)
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
        getData()
    }

    fun getData() {
        baseViewModelScope.launch {
            try {
                when (val response = getAllUseCase.execute()) {
                    is UseCaseResponse.Error -> {
                        mutableStateFlow.value = AppState.Error(response.message)
                    }
                    is UseCaseResponse.Success<*> -> {
                        val data = (response.data as QuerySnapshot)
                        val listData: List<DomainData> = data.documents.map { document ->
                            val date: Long = document.data?.get("date") as Long
                            val pressure: String = document.data?.get("pressure") as String
                            val pulse: String = document.data?.get("pulse") as String
                            DomainData(
                                date = GregorianCalendar().also { calendar ->
                                    calendar.timeInMillis = date
                                },
                                pressure = pressure,
                                pulse = pulse.toInt()
                            )
                        }.sortedBy{ it.date.timeInMillis }
                        mutableStateFlow.value = AppState.Success(listData)
                    }
                }
            } catch (e: Exception) {
                mutableStateFlow.value = AppState.Error(e.message.toString())
            }
        }
    }
}