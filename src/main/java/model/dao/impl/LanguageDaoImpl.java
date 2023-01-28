package model.dao.impl;

import exception.DaoOperationException;
import model.dao.LanguageDao;
import model.dao.util.DataSource;
import model.dao.util.DataSourceUtil;
import model.dao.util.EntityInitialization;
import model.entity.Language;
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
    private final static String SELECT_ID_BY_LANGUAGE_NAME = "SELECT id_language FROM languages WHERE language_name = ?";
   private final static Logger logger = Logger.getLogger(LanguageDaoImpl.class);

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
            logger.info("Find all languages");
            return movieList;
        } catch (SQLException e) {
            logger.error("Error finding all movies by language id", e);
            throw new DaoOperationException("Error finding all movies by language id", e);
        }finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }

    /**
     * Returns id of language by language name
     *
     * @return id of language
     * @throws DaoOperationException if there was an error executing the query
     *                      in the database
     * @see Language
     */
    @Override
    public Long getIdByName(String name) throws DaoOperationException {
        ResultSet rs = null;
        try(Connection connection = DataSource.getInstance().getConnection();
            PreparedStatement pr = connection.prepareStatement(SELECT_ID_BY_LANGUAGE_NAME)){
            pr.setString(1,name);
            rs = pr.executeQuery();
            if (rs.next()){
                logger.info(String.format("Get language id by language name = %s", name));
                return rs.getLong("id_language");
            }else {
                logger.error(String.format("Language with name = %s does not exist", name));
                throw new DaoOperationException(String.format("Language with name = %s does not exist", name));
            }
        }catch (SQLException e){
            throw new DaoOperationException(String.format("Error finding language with name = %s", name), e);
        } finally{
            DataSourceUtil.closeResultSet(rs);
        }
    }
}
