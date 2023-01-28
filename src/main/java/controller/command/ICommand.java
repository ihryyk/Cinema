package controller.command;

import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * An interface which has a basic Command method.
 *
 */
public interface ICommand {


    /**
     * Executes the request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     *
     * @throws DaoOperationException if any inner exception in dao occurs
     * @throws IOException      if I/O error occurs.
     * @throws TransactionException if error occurred during the transaction
     */
    String execute(HttpServletRequest request , HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException;
}
