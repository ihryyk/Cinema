package model.dao.impl;

import exception.DaoOperationException;
import model.dao.MovieDescriptionDao;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Movie;
import model.entity.MovieDescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement an interface that defines different activities with movie description in database.
 *
 */
public class MovieDescriptionDaoImpl implements MovieDescriptionDao {
    private static final String SELECT_MOVIE_DESCRIPTION_BY_MOVIE_ID = "SELECT * FROM movie_descriptions INNER JOIN languages l on l.id_language = movie_descriptions.language_id WHERE movie_id=?;";

    /**
     * Returns list of movie description from database by movie id
     * @param id - id of movie
     * @return list of movie description
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see MovieDescription
     */
    public List<MovieDescription> findByMovieId(long id) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIE_DESCRIPTION_BY_MOVIE_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            return collectToList(rs);
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Reads movies description information from result set and collect him to list
     * @param resultSet - result set with information about movies description
     * @return list of movies description
     * @throws SQLException - if there was an error executing the query
     *                        in the database
     * @see MovieDescription
     */
    private List<MovieDescription> collectToList(ResultSet resultSet) throws SQLException {
        List<MovieDescription> movieDescriptionList = new ArrayList<>();
        while (resultSet.next()){
            MovieDescription movieDescription = EntityInitialization.movieDescriptionInitialization(resultSet);
            movieDescription.setLanguage(EntityInitialization.languageInitialization(resultSet));
            movieDescriptionList.add(movieDescription);
        }
        return movieDescriptionList;
    }
}
