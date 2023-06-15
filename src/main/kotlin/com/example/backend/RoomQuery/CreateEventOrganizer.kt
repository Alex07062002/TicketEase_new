package com.example.backend.RoomQuery

import com.example.DataClasses.Ticket.TicketDTO
import com.example.backend.DataClasses.TicketCountWithPrice
import com.example.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.example.backend.PostgreSQL.Transactions.TicketTransactionImpl


class CreateEventOrganizer {

    private val ticketService = TicketTransactionImpl()

    suspend fun createListTicket(ticketParams : TicketCountWithPrice) = dbQuery {
        if ((ticketParams.row != null) && (ticketParams.column != null)){
            for (row in 1 .. ticketParams.row) {
                for(column in 1 .. ticketParams.column){
                    ticketService.createTicket(TicketDTO(eventId = ticketParams.eventId,row = row, column = column, price = ticketParams.price))
                }
            }
        }else{
            for (ticket in 1 .. ticketParams.capacity){
                ticketService.createTicket(TicketDTO(eventId = ticketParams.eventId,price = ticketParams.price))
            }
        }
    }
}


