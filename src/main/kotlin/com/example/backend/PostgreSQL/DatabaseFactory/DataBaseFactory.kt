package com.example.backend.PostgreSQL.DatabaseFactory

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Favorites.FavoriteTable
import com.example.DataClasses.Person.OrganizerTable
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.example.backend.DataClasses.PlaceTime.PlaceTimeTable
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseFactory {

    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://db:5432/TicketEase?user=postgres"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(OrganizerTable, EventTable, OrganizerTable, PlaceTimeTable,
                PlaceTable, TicketTable)
        }
    }


    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}
