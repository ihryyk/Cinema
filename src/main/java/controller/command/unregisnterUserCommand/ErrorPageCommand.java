package controller.command.unregisnterUserCommand;

import controller.command.ICommand;
import controller.command.authCommand.RegisterCommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Implementation of Command interface that perform displaying error page.
 */
public class ErrorPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(ErrorPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Error page command");
        return "error";
    }
}
