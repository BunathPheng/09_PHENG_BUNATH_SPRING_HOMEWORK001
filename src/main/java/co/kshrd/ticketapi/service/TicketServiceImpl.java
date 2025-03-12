package co.kshrd.ticketapi.service;

import co.kshrd.ticketapi.model.dto.ApiRespone;
import co.kshrd.ticketapi.model.Ticket;
import co.kshrd.ticketapi.model.dto.TicketRequestDto;
import co.kshrd.ticketapi.model.dto.TicketsStatus;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    List<Ticket> tickets = new ArrayList<>();

    public TicketServiceImpl() {
        tickets.add(new Ticket(1, "John Doe", "2025-03-15", "New York", "Los Angeles", 150.5, true, TicketsStatus.COMPLETED.name(), "A1"));
        tickets.add(new Ticket(2, "Jane Smith", "2025-03-16", "Chicago", "San Francisco", 200.0, false, TicketsStatus.COMPLETED.name(), "B2"));
        tickets.add(new Ticket(3, "Alice Brown", "2025-03-17", "Boston", "Miami", 180.75, true, TicketsStatus.BOOKED.name(), "C3"));
        tickets.add(new Ticket(3, "Alice Brown", "2025-03-17", "Boston", "Miami", 180.75, true, TicketsStatus.CANCELLED.name(), "C3"));
    }


    @Override
    public ResponseEntity<ApiRespone<List<Ticket>>> getAllTickets() {
        ApiRespone<List<Ticket>> response = new ApiRespone<>(
                true,
                "Tickets retrieved successfully",
                "OK",
                tickets,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiRespone<Ticket>> createTicket(TicketRequestDto ticketRequestDto) {
        ApiRespone<Ticket> response = new ApiRespone<>();
        if (ticketRequestDto.getTicketPrice() > 0) {
            Ticket ticket = new Ticket(
                    tickets.size() + 1,
                    ticketRequestDto.getPassengerName(),
                    ticketRequestDto.getTravelDate(),
                    ticketRequestDto.getSourceStation(),
                    ticketRequestDto.getDestinationStation(),
                    ticketRequestDto.getTicketPrice(),
                    ticketRequestDto.isPaymentStatus(),
                    ticketRequestDto.getTicketStatus().name(),
                    ticketRequestDto.getSeatNumber()
            );
            tickets.add(ticket);
            response = new ApiRespone<>(
                    true,
                    "Tickets created successfully",
                    "CREATED",
                    ticket,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ApiRespone<Ticket>> updateTicket(Integer ticketId, TicketRequestDto ticketRequestDto) {
        ApiRespone<Ticket> response = new ApiRespone<>();

        if (ticketRequestDto.getTicketPrice() <= 0) {
            response.setSuccess(false);
            response.setMessage("Ticket price must be greater than 0.");
            response.setStatus("Not found");
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Optional<Ticket> ticketOptional = tickets.stream()
                .filter(t -> t.getTicketId() == ticketId)
                .findFirst();

        if (ticketOptional.isPresent()) {
            Ticket ticketToUpdate = ticketOptional.get();
            ticketToUpdate.setPassengerName(ticketRequestDto.getPassengerName());
            ticketToUpdate.setTravelDate(ticketRequestDto.getTravelDate());
            ticketToUpdate.setSourceStation(ticketRequestDto.getSourceStation());
            ticketToUpdate.setDestinationStation(ticketRequestDto.getDestinationStation());
            ticketToUpdate.setTicketPrice(ticketRequestDto.getTicketPrice());
            ticketToUpdate.setPaymentStatus(ticketRequestDto.isPaymentStatus());
            ticketToUpdate.setTicketStatus(ticketRequestDto.getTicketStatus().name().toLowerCase());
            ticketToUpdate.setSeatNUmber(ticketRequestDto.getSeatNumber());


            response.setSuccess(true);
            response.setMessage("Ticket updated successfully.");
            response.setPayload(ticketToUpdate);
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setSuccess(false);
            response.setMessage("Ticket not found.");
            response.setStatus("Not Found");
            response.setMessage("Ticket with ID " + ticketId + " not found.");
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiRespone<Ticket>> getTicketById(Integer id) {
        ApiRespone<Ticket> response = new ApiRespone<>();

        Optional<Ticket> ticketOptional = tickets.stream()
                .filter(t -> t.getTicketId() == id)
                .findFirst();
        if (ticketOptional.isPresent()) {
            response.setSuccess(true);
            response.setMessage("Ticket with ID " + id + " found.");
            response.setStatus("OK");
            response.setPayload(ticketOptional.get());
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            response.setSuccess(false);
            response.setMessage("Ticket with ID " + id + " not found.");
            response.setStatus("Not Found");
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiRespone<Ticket>> deleteTicket(Integer ticketId) {
        ApiRespone<Ticket> response = new ApiRespone<>();
        Optional<Ticket> optionalTicket = tickets.stream().filter(t -> t.getTicketId() == ticketId).findFirst();
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            tickets.remove(ticket);
            response.setSuccess(true);
            response.setMessage("Ticket with ID " + ticketId + " deleted.");
            response.setStatus("OK");
            response.setPayload(ticket);
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setSuccess(false);
            response.setMessage("Ticket with ID " + ticketId + " not found.");
            response.setStatus("Not Found");
            response.setPayload(null);
            response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiRespone<List<Ticket>>> searchByPassengerName(String passengerName) {
        List<Ticket> matchingTickets = tickets.stream()
                .filter(t -> t.getPassengerName() != null &&
                        t.getPassengerName().toUpperCase().contains(passengerName.trim().toUpperCase()))
                .collect(Collectors.toList());

        ApiRespone<List<Ticket>> response = new ApiRespone<>();
        response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        if (!matchingTickets.isEmpty()) {
            response.setPayload(matchingTickets);
            response.setSuccess(true);
            response.setMessage("Tickets found with " + passengerName);
            response.setStatus(HttpStatus.OK.name());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setSuccess(false);
            response.setMessage(String.format("No tickets found for passenger name '%s'", passengerName));
            response.setStatus(HttpStatus.NOT_FOUND.name());
            response.setPayload(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ApiRespone<List<Ticket>>> filterByTicketStatusAndTravelDate(
            String ticketStatus, String travelDate) {

        List<Ticket> filteredTickets = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus() == ticketStatus &&
                    ticket.getTravelDate().equals(travelDate)) {
                filteredTickets.add(ticket);
            }
        }
        ApiRespone<List<Ticket>> response = new ApiRespone<>();
        if (filteredTickets.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Tickets filtered fail");
            response.setStatus("Not Found");
            response.setPayload(Collections.emptyList());
            response.setTimestamp(LocalDateTime.now().toString());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }else {
            response.setSuccess(true);
            response.setMessage("Tickets filtered successfully");
            response.setStatus("200 OK");
            response.setPayload(filteredTickets);
            response.setTimestamp(LocalDateTime.now().toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
