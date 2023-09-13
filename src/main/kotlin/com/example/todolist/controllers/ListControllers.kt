package com.example.todolist.controllers

import com.example.todolist.repositories.Repo
import com.example.todolist.model.Lists
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController

@RequestMapping ("/todo")
class ListControllers (private val todoRepository : Repo ){

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun read():List<Lists>{
        return todoRepository.findAll(Sort.by(Sort.Order.asc("order")))
    }


    @GetMapping("/createddate")
    fun getByDateCreated():List<Lists>{
        return todoRepository.findAll(Sort.by(Sort.Order.desc("createdDate")))
    }

    @GetMapping("/deliverydate")
    fun getByDeliveryDate():List<Lists>{
        return todoRepository.findAll(Sort.by(Sort.Order.asc("Delivery_date")))
    }

    @PostMapping
    fun create(@RequestBody todoItem: Lists): Lists{
        return todoRepository.save(todoItem)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody updatedItem: Lists):Lists{
        val existingItem = todoRepository.findById(updatedItem.id).orElse(null)
                ?:throw NoSuchElementException("There is No List with this Id")

//          val todoItem = existingItem.get()
       existingItem.id = updatedItem.id
       existingItem.title = updatedItem.title
       existingItem.done = updatedItem.done
        existingItem.order = updatedItem.order
        //existingItem.createdDate = updatedItem.createdDate
        existingItem.deliveryDate = updatedItem.deliveryDate

        return todoRepository.save(existingItem)

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String){
        todoRepository.deleteById(id)
    }
}