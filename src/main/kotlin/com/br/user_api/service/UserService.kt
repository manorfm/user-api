package com.br.user_api.service

import com.br.user_api.repository.User
import com.br.user_api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun getAll(): List<User> {
        return userRepository.findAll()
    }
}