package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Used to store information about purhcased seat.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedSeat {
   private Long id;
   private Seat seat;
   private Session session;
}
