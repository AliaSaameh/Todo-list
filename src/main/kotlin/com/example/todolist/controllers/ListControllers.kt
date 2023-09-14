package com.example.todolist.controllers

import com.example.todolist.repositories.Repo
import com.example.todolist.model.Lists
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity



import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
//import java.time.Instant
//import java.time.LocalDate


@RestController

@RequestMapping ("/todo")
class ListControllers (private val todoRepository : Repo ){

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)



    @GetMapping ("/{id}")
    fun getList(@PathVariable id: String):Lists?{
        return todoRepository.findById(id).orElse(null)

    }


    @GetMapping ("/lists")
    fun getAllLists(

        @RequestParam(name = "sortBy", defaultValue = "createdDate") sortBy: String,
        @RequestParam(name = "sortDirection", defaultValue = "asc") sortDirection: String,

        ):List<Lists>{

        val sort = Sort.by(Sort.Order(Direction.fromString(sortDirection), sortBy))

        return todoRepository.findAll(sort)


    }


    @PostMapping
    fun create(@RequestBody todoItem: Lists): Lists{
        return todoRepository.save(todoItem)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody updatedItem: Lists):Lists{
        val existingItem = todoRepository.findById(updatedItem.id).orElse(null)
            ?:throw NoSuchElementException("There is No List with this Id")

        /* existingItem.id = updatedItem.id
         existingItem.title = updatedItem.title
         existingItem.done = updatedItem.done
         existingItem.order = updatedItem.order
         existingItem.deliveryDate = updatedItem.deliveryDate*/

        updatedItem.title?.let { existingItem.title = it }
        updatedItem.done?.let { existingItem.done = it }
        updatedItem.order?.let { existingItem.order = it }
        updatedItem.deliveryDate?.let { existingItem.deliveryDate = it }

        return todoRepository.save(existingItem)

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String){
        todoRepository.deleteById(id)
    }

   @DeleteMapping("/all")
    fun deleteAll() {
        todoRepository.deleteAll()
    }
}