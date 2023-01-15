package model.dao.impl;

import exception.DaoOperationException;
import model.dao.MovieDescriptionDao;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.MovieDescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDescriptionDaoImpl implements MovieDescriptionDao {
    private static final String SELECT_MOVIE_DESCRIPTION_BY_MOVIE_ID = "SELECT * FROM movie_descriptions INNER JOIN languages l on l.id_language = movie_descriptions.language_id WHERE movie_id=?;";
    public List<MovieDescription> findByMovieId(Long id) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_MOVIE_DESCRIPTION_BY_MOVIE_ID)){
            pr.setLong(1,id);
            rs = pr.executeQuery();
            List<MovieDescription> movieDescriptionList = new ArrayList<>();
            while (rs.next()){
                movieDescriptionList.add(EntityInitialization.movieDescriptionInitialization(rs));
            }
            return movieDescriptionList;
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
