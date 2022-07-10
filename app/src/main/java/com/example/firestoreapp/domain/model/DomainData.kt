package com.example.firestoreapp.domain.model

import java.util.*

data class DomainData(
    val date: GregorianCalendar,
    val pressure: String,
    val pulse: Int,
)
