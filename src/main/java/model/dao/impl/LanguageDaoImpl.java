package model.dao.impl;

import exception.DaoOperationException;
import model.dao.LanguageDao;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Language;
import model.entity.Movie;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement an interface that defines different activities with language in database.
 *
 */
public class LanguageDaoImpl implements LanguageDao {
    private final static String SELECT_ALL_LANGUAGES = "SELECT * FROM languages";
    Logger logger = Logger.getLogger(LanguageDaoImpl.class);

    /**
     * Returns list of language from database
     *
     * @return list of language.
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Language
     */
    @Override
    public List<Language> finaAll() throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ALL_LANGUAGES)){
            rs = pr.executeQuery();
            List<Language> movieList = new ArrayList<>();
            while (rs.next()){
                movieList.add(EntityInitialization.languageInitialization(rs));
            }
            return movieList;
        } catch (SQLException e) {
            throw new DaoOperationException("Error finding all movies by language id", e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
