package com.example.todolist.model



import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate

@Document
class Lists {
    @Id var id: String = ""
    var title: String =""
    var done: Boolean = false
    var createdDate: Instant = Instant.now()
    var deliveryDate: LocalDate? = null
    var order: Int = 0
}