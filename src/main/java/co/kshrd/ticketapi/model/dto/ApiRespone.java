package co.kshrd.ticketapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRespone<T> {
    private boolean success;
    private String message;
    private String status;
    private T payload;
    private String timestamp;
}
