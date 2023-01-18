package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
/**
 * Used to store information about movie description.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDescription {
    private String title;
    private String director;
    private Language language;
}
