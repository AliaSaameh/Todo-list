package com.example.todolist.repositories

import com.example.todolist.model.Lists
import org.springframework.data.mongodb.repository.MongoRepository

interface Repo : MongoRepository<Lists, String>


