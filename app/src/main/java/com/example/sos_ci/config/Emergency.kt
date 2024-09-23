package com.example.sos_ci.config

data class Emergency(
    val title: String = "",
    val phoneNumbers: List<String> = emptyList(),
    val category: String = ""
)
