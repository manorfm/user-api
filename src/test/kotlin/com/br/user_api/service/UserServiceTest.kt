package com.br.user_api.service

import com.br.user_api.repository.User
import com.br.user_api.repository.UserRepository
import com.br.user_api.service.exception.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers
import org.mockito.kotlin.*

import java.time.LocalDate
import java.util.*

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

    @Test
    fun `given an user id then should return a respective user`() {
        val id = "9123872";
        val userExpected = User(
            id,
            first_name = "TDD",
            last_name = "Test",
            date_of_birth = LocalDate.of(1900, 2, 1)
        )

        val userRepository = mock<UserRepository> {
            on { findById(id) } doReturn Optional.of(userExpected)
        }

        val userService = UserService(userRepository)
        val user = userService.get(id)

        assertEquals(userExpected, user)

        verify(userRepository, times(1)).findById(id)
    }

    @Test
    fun `given an invalid user id then should return null`() {
        val id = "9123872";

        val user = User(
            id,
            first_name = "TDD",
            last_name = "Test",
            date_of_birth = LocalDate.of(1900, 2, 1)
        )

        val userRepository = mock<UserRepository> {
            on { save(user) } doReturn user
        }

        val userService = UserService(userRepository)
        userService.save(user)

        assertEquals(user, user)

        verify(userRepository, times(1)).save(user)
    }

    @Test
    fun `given an user then should save it`() {
        val id = "123"
        val userRepository = mock<UserRepository> {
            on { findById(ArgumentMatchers.anyString()) } doReturn Optional.empty()
        }

        val userService = UserService(userRepository)

        assertThrows<UserNotFoundException> {
            userService.get(id)
        }

        verify(userRepository, times(1)).findById(id)
    }

    @Test
    fun `given an user id then should delete it`() {
        val id = "123"
        val userRepository = mock<UserRepository> {
            on { deleteById(ArgumentMatchers.anyString()) } doAnswer {}
        }

        val userService = UserService(userRepository)

        userService.delete(id)

        verify(userRepository, times(1)).deleteById(id)
    }
}