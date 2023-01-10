package model.dao;

import model.entity.Movie;
import model.entity.MovieDescription;

public interface MovieDescriptionDao {
    public void save(MovieDescription movieDescription);
    public void update(MovieDescription movieDescription);
    public void findALL();
}
