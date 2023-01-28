package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Used to store information about seat.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat implements Comparable<Seat> {
    private Long id;
    private int row;
    private int number;

    @Override
    public int compareTo(Seat seat) {
        return this.getRow()-seat.getRow();
    }
}
