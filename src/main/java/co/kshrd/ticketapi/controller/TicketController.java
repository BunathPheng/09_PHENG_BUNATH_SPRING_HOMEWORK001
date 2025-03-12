package co.kshrd.ticketapi.controller;
import co.kshrd.ticketapi.model.Ticket;
import co.kshrd.ticketapi.model.dto.ApiRespone;
import co.kshrd.ticketapi.model.dto.TicketRequestDto;
import co.kshrd.ticketapi.model.dto.TicketsStatus;
import co.kshrd.ticketapi.model.dto.UpdateRequestDto;
import co.kshrd.ticketapi.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")

public class TicketController {
    private final TicketService ticketService;
     public TicketController(TicketService ticketService) {
         this.ticketService = ticketService;
     }
     @GetMapping
     @Operation(summary = "Get all tickets")
     public ResponseEntity<ApiRespone<List<Ticket>>> getTicket() {
         return ticketService.getAllTickets();
     }
     @PostMapping
     @Operation(summary = "Create new tickets")
     public ResponseEntity<ApiRespone<Ticket>> createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
         return ticketService.createTicket(ticketRequestDto);
     }
     @PutMapping("/{ticket-id}")
     @Operation(summary = "Update an existing tickets")
     public ResponseEntity<ApiRespone<Ticket>> updateTicket(@PathVariable("ticket-id") Integer ticketId, @RequestBody TicketRequestDto ticketRequestDto) {
         return ticketService.updateTicket(ticketId , ticketRequestDto);
     }
     @GetMapping("{ticket-id}")
     @Operation(summary = "Get a ticket by ID")
     public ResponseEntity<ApiRespone<Ticket>> getTicketById(@PathVariable("ticket-id") Integer ticketId) {
         return ticketService.getTicketById(ticketId);
     }
     @DeleteMapping("{ticket-id}")
     @Operation(summary = "Delete a  tickets By ID")
     public ResponseEntity<ApiRespone<Ticket>> deleteTicket(@PathVariable("ticket-id") Integer ticketId) {
         return ticketService.deleteTicket(ticketId);
     }
     @GetMapping("/search")
     @Operation(summary = "Search tickets by passengerName")
     public ResponseEntity<ApiRespone<List<Ticket>>> searchTicket(@RequestParam("passengerName") String passengerName) {
         return ticketService.searchByPassengerName(passengerName);
     }
     @GetMapping("/filter")
     @Operation(summary = "Filter tickets by ticketStatus and travelDate")
    public ResponseEntity<ApiRespone<List<Ticket>>> filterTicket(@RequestParam TicketsStatus ticketStatus,
                                                                 @RequestParam String travelDate) {
         return ticketService.filterByTicketStatusAndTravelDate(ticketStatus, travelDate);

     }
     @PostMapping("/bulk")
     @Operation(summary = "Get all bulks")
    public ResponseEntity<ApiRespone<List<Ticket>>> bulkTicket(@RequestBody List<TicketRequestDto> ticketRequestDto) {
         return ticketService.createTicketByBulk(ticketRequestDto);
     }
     @PutMapping("/tickets")
     @Operation(summary = "Update by payments status and multiple id")
        public ResponseEntity<ApiRespone<List<Ticket>>> updateTicket(@RequestBody UpdateRequestDto updateRequestDto) {
         return ticketService.updateTicketStatusAndMultipleId(
                 updateRequestDto);

     }
}

