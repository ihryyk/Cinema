package controller.command.adminCommand;

import controller.command.ICommand;
import controller.util.Localization;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.MovieService;
import service.ServiceFactory;

/**
 * Implementation of Command interface that perform displaying admin page.
 */
public class AdminPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(AdminPageCommand.class);
    private final MovieService movieService = ServiceFactory.getMovieService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        Localization.addLocalization(request);
        if (request.getParameter("movieName")!=null){
            request.setAttribute("movies", movieService.findByLanguageAndTitle(1L,request.getParameter("movieName")));
        }else
            request.setAttribute("movies", movieService.findAllByLanguage(1L));
        logger.info("Admin page page command");
        return "adminIndex";
    }
}
