package com.br.user_api.service

import com.br.user_api.repository.User
import com.br.user_api.repository.UserRepository
import com.br.user_api.service.exception.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun getAll(): List<User> {
        return userRepository.findAll()
    }

    fun get(id: String): User {
        val user = userRepository.findById(id)

        if (user.isPresent) return user.get()

        throw UserNotFoundException()
    }

    fun save(user: User) {
        userRepository.save(user)
    }

    fun delete(id: String) {
        userRepository.deleteById(id)
    }
}