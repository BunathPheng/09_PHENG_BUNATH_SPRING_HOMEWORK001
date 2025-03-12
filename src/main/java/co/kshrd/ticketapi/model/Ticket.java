package co.kshrd.ticketapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private int ticketId;
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double ticketPrice;
    private boolean paymentStatus;
    private String ticketStatus;
    private String seatNUmber;
}
