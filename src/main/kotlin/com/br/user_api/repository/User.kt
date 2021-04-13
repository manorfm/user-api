package com.br.user_api.repository

import java.time.LocalDate

data class User(
    val id: String,
    val first_name: String,
    val last_name: String,
    val date_of_birth: LocalDate
)
