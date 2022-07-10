package com.example.firestoreapp.data

import com.example.firestoreapp.data.firestoredatabase.model.FireStoreData
import com.example.firestoreapp.domain.model.DomainData

fun DomainData.toFireStoreData() = FireStoreData(
    date = this.date,
    pressure = this.pressure,
    pulse = this.pulse,
)

fun List<FireStoreData>.toListDomainData() = this.map {
    DomainData(
        date = it.date,
        pressure = it.pressure,
        pulse = it.pulse,
    )
}