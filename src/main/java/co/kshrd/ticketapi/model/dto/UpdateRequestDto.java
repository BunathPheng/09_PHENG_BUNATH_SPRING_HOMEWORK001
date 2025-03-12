package co.kshrd.ticketapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDto<T> {
    private List<Integer> id;
    private boolean paymentStatus ;
}
