package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.MovieFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * Used to store information about session.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Long id;
    private Movie movie;
    private Timestamp startTime;
    private Timestamp endTime;
    private MovieFormat format;
    private BigDecimal price;
    public int availableSeats;
}
