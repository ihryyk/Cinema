package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
/**
 * Used to store information about seat.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private Long id;
    private int row;
    private int number;
}
