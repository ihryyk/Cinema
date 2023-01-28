package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.enums.MovieFormat;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Implementation of Command interface that perform displaying add session page.
 */
public class AddSessionPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(AddSessionPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        request.setAttribute("movieId", movieId);
        request.setAttribute("formats", Arrays.asList(MovieFormat.values()));
        logger.info("Add session page command");
        return "addSession";
    }
}
