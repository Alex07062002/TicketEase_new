package com.example.backend.RoomQuery

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Event.StatusEvent
import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.example.backend.DataClasses.Catalog
import com.example.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.example.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.example.backend.PostgreSQL.Transactions.OrganizerTransactionImpl
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class CatalogRoom {
    private val event = EventTable
    private val ticket = TicketTable
    private val place = PlaceTable
    private val placeTime = PlaceTimeTable
    private val organizerService = OrganizerTransactionImpl()

    private fun toCatalogEntity(rs : ResultRow) = Catalog(
        eventId = rs[event.id].value,
        name = rs[event.name],
        price = rs[ticket.price],
        location = rs[place.location],
        date = rs[placeTime.date]
    )

    suspend fun catalogRoom(city : Cities) : List<Catalog> = dbQuery{
        val listOrganizerId : List<Long> = organizerService.selectOrganizerByCity(city)
        place.join(placeTime,JoinType.INNER,place.id,placeTime.placeId)
            .join(event, JoinType.INNER,placeTime.id,event.placeTimeId, additionalConstraint = {
                event.organizerId inList listOrganizerId;event.status eq StatusEvent.CREATED.toString()})
            .join(ticket,JoinType.LEFT,event.id,ticket.eventId)
            .slice(event.id,event.name,ticket.price,place.location,placeTime.date)
            .selectAll().withDistinct(true).map(::toCatalogEntity)
    }
}