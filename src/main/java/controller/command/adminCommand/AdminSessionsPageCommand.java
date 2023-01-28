package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.MovieService;
import service.SeatService;
import service.ServiceFactory;
import service.SessionService;
/**
 * Implementation of Command interface that perform displaying admin sessions page.
 */
public class AdminSessionsPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(AdminSessionsPageCommand.class);
    private final MovieService movieService = ServiceFactory.getMovieService();
    private final SessionService sessionService =ServiceFactory.getSessionService();
    private final SeatService seatService = ServiceFactory.getSeatService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        Long movieId = Long.valueOf(request.getParameter("movieId"));
        request.setAttribute("movieId",movieId);
        request.setAttribute("sessions", sessionService.findByMovieId(movieId));
        request.setAttribute("numberSpectators", seatService.getNumberBusySeatAllSessionByMovieId(movieId));
        request.setAttribute("movie", movieService.findByIdAndLanguageId(movieId,(Long) request.getSession().getAttribute("dbLanguage")));
        logger.info("Admin session page command");
        return  "adminSession";
    }
}
