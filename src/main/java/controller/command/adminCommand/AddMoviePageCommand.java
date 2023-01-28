package controller.command.adminCommand;

import controller.command.ICommand;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.LanguageService;
import service.ServiceFactory;

/**
 * Implementation of Command interface that perform displaying add movie page.
 */
public class AddMoviePageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(AddMoviePageCommand.class);
    private final LanguageService languageService = ServiceFactory.getLanguageService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        request.setAttribute("languages",languageService.findAll());
        logger.info("Add movie page command");
        return "addMovie";
    }
}
