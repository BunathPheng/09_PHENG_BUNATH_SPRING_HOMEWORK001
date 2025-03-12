package co.kshrd.ticketapi.service;
import co.kshrd.ticketapi.model.Ticket;
import co.kshrd.ticketapi.model.dto.ApiRespone;

import co.kshrd.ticketapi.model.dto.TicketRequestDto;
import co.kshrd.ticketapi.model.dto.TicketsStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {
    ResponseEntity<ApiRespone<List<Ticket>> > getAllTickets();
    ResponseEntity<ApiRespone<Ticket>> createTicket(TicketRequestDto ticketRequestDto);
    ResponseEntity<ApiRespone<Ticket>> updateTicket(Integer ticketId, TicketRequestDto ticketRequestDto);
    ResponseEntity<ApiRespone<Ticket>> getTicketById(Integer id);
    ResponseEntity<ApiRespone<Ticket>> deleteTicket(Integer ticketId);
    ResponseEntity<ApiRespone<List<Ticket>>> searchByPassengerName(String passengerName);
    public ResponseEntity<ApiRespone<List<Ticket>>> filterByTicketStatusAndTravelDate(
            String ticketStatus,
            String travelDate);
}
