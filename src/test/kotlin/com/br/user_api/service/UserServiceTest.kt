package com.br.user_api.service

import com.br.user_api.repository.User
import com.br.user_api.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.mockito.kotlin.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.time.LocalDate

class UserServiceTest {

    @Test
    fun `given method to get all user when have an empty database then should return an empty list of users`() {
        val userRepository = mock<UserRepository> {
            on { findAll() } doReturn emptyList()
        }

        val userService = UserService(userRepository)
        val users = userService.getAll()

        assertEquals(0, users.size)

        verify(userRepository, times(1)).findAll()
    }

    @Test
    fun `given a method to get all user then should return a list of users`() {
        val user = User(
            id = "9123872",
            first_name = "TDD",
            last_name = "Test",
            date_of_birth = LocalDate.of(1900, 2, 1)
        )

        val userRepository = mock<UserRepository> {
            on { findAll() } doReturn listOf(user)
        }

        val userService = UserService(userRepository)
        val users = userService.getAll()

        assertEquals(1, users.size)
        assertEquals(user, users[0])

        verify(userRepository, times(1)).findAll()
    }
}