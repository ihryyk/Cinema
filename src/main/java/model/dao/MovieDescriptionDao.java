package model.dao;

import exception.DaoOperationException;
import model.entity.MovieDescription;

import java.util.List;
/**
 * The interface defines methods for implementing different
 * activities with movie description
 *
 */
public interface MovieDescriptionDao {

    /**
     * Returns list of movie description from database by movie id
     * @param id - id of movie
     * @return list of movie description
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see MovieDescription
     */
    public List<MovieDescription> findByMovie(long id) throws DaoOperationException;
}
