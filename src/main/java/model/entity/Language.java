package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
/**
 * Used to store information about language.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {
    private Long id;
    private String name;
}
