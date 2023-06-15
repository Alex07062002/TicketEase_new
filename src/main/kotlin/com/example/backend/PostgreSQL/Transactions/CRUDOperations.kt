package com.example.backend.PostgreSQL.Transactions

interface CRUDOperations<T,E> {

    suspend fun selectAll() : List<T>
    suspend fun selectById(id : E) : T?
}


