package model.dao.util;

import exception.DaoOperationException;
import exception.TransactionException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceUtil {
    /**
     * Close ResultSet.
     *
     * @param resultSet - ResultSet.
     *
//     * @throws DAOException  if there was an error in the closing of
     * the ResultSet.
     *
     */
    static public void closeResultSet(ResultSet resultSet) throws DaoOperationException {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new  DaoOperationException("ResultSet closure failed", e);
        }
    }

    /**
     * The method cancels the transaction.
     *
     * @param con - connection to database.
     *
     * @throws TransactionException  If an error occurred canceling a transaction.
     *
     */
    public static void rollback(Connection con) throws TransactionException {
        try {
            con.rollback();
            con.setAutoCommit(true);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new TransactionException("Rolling back failed.", e);
        }
    }
    /**
     * The method closes the transaction.
     *
     * @param con - connection to database.
     *
     * @throws TransactionException  In the event of a transaction closing error.
     *
     */
    public static void closeTransaction(Connection con) throws TransactionException {
        try {
            if (con != null) {
                con.commit();
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new TransactionException("Close transaction failed.", e);
        }

    }
    /**
     * Close connection.
     *
     * @param con - Сonnection.
     *
//     * @throws DAOException  if there was connection return error.
     *
//     * @see ConnectionPool
     */
    public static void closeConnection(Connection con) throws DaoOperationException  {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoOperationException("Close connection failed.", e);
        }
    }

    /**
     * Close Statement.
     *
     * @param statement - Statement.
     *
//     * @throws DAOException  if there was an error in the closing of
     * the Statement.
     *
     */
    public static void closeStatement(Statement statement) throws DaoOperationException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Close transaction failed.", e);
        }
    }


}
