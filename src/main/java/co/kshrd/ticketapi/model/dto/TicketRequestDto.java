package co.kshrd.ticketapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
public class TicketRequestDto {
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double ticketPrice;
    private boolean paymentStatus;
    private TicketsStatus ticketStatus;
    private String seatNumber;
}
