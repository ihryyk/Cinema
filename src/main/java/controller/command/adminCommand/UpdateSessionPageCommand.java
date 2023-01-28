package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.enums.MovieFormat;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.SessionService;

import java.util.Arrays;

/**
 * Implementation of Command interface that perform displaying update session page.
 */
public class UpdateSessionPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(UpdateSessionPageCommand.class);
    private final SessionService sessionService = ServiceFactory.getSessionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        request.setAttribute("formats", Arrays.asList(MovieFormat.values()));
        request.setAttribute("movieId", movieId);
        request.setAttribute("session", sessionService.findByIdAndLanguageId(Long.valueOf(request.getParameter("sessionId")),(Long) request.getSession().getAttribute("dbLanguage")));
        logger.info("Update session page command");
        return "updateSession";
    }
}
