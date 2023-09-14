package com.example.todolist.repositories

import com.example.todolist.model.Lists
import java.time.Instant
import java.time.LocalDate
import org.springframework.data.mongodb.repository.MongoRepository

interface Repo : MongoRepository<Lists, String>{

}


