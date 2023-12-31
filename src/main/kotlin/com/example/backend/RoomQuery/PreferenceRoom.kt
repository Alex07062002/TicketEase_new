package com.example.backend.RoomQuery

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Event.StatusEvent
import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.example.backend.DataClasses.Catalog
import com.example.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.example.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.example.backend.PostgreSQL.Transactions.BuyerTransactionImpl
import com.example.backend.PostgreSQL.Transactions.EventTransactionImpl
import com.example.backend.PostgreSQL.Transactions.OrganizerTransactionImpl
import com.example.backend.PostgreSQL.Transactions.TicketTransactionImpl
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class PreferenceRoom{
    private val event = EventTable
    private val ticket = TicketTable
    private val place = PlaceTable
    private val placeTime = PlaceTimeTable
    private val eventService = EventTransactionImpl()
    private val ticketService = TicketTransactionImpl()
    private val buyerService = BuyerTransactionImpl()
    private val organizerService = OrganizerTransactionImpl()

   private fun toCatalogEntity(rs : ResultRow) = Catalog(
       eventId = rs[event.id].value,
        name = rs[event.name],
        price = rs[ticket.price],
        location = rs[place.location],
        date = rs[placeTime.date]
    )

    suspend fun preferencesRoom(token : String, city : Cities) = dbQuery {
        val buyerId : Long = buyerService.selectIdByToken(token)
        val listEventId : List<Long> = ticketService.selectEventByBuyer(buyerId)
        val genreList : List<String> = eventService.selectGenreForPreferences(listEventId)
        val listOrganizerId : List<Long> = organizerService.selectOrganizerByCity(city)
        place.join(placeTime, JoinType.INNER, place.id, placeTime.placeId)
            .join(event, JoinType.INNER, placeTime.id, event.placeTimeId, additionalConstraint = {
                event.organizerId inList listOrganizerId; event.genre inList genreList
                event.status eq StatusEvent.CREATE.toString() })
            .join(ticket, JoinType.INNER, event.id, ticket.eventId)
            .slice(event.id,event.name, ticket.price, place.location, placeTime.date)
            .selectAll().withDistinct(true).map(::toCatalogEntity)
    }
}