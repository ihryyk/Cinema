package model.dao;

import exception.DaoOperationException;
import model.entity.Movie;
import model.entity.MovieDescription;

import java.util.List;

public interface MovieDescriptionDao {
    public List<MovieDescription> findByMovieId(Long id) throws DaoOperationException;
}
