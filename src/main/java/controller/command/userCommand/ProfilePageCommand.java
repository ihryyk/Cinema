package controller.command.userCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Implementation of Command interface that perform displaying profile page.
 */
public class ProfilePageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(ProfilePageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("Profile page command");
        return "profile";
    }
}
