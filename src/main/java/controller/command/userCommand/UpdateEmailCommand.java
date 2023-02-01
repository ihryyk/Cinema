package controller.command.userCommand;

import controller.command.ICommand;
import controller.validator.Validator;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import java.io.IOException;

/**
 * Implementation of a command line interface that performs email updates
 */
public class UpdateEmailCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(UpdateEmailCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("Update email command");
        String email = request.getParameter("email");
        long id = Long.parseLong(request.getParameter("id"));
        if (Validator.isValidEmail(email)){
            if (userService.findByEmail(email)==null){
                userService.updateEmail(email,id);
                request.getSession().setAttribute("user", userService.findById(id));
                request.getSession().setAttribute("popUpsSuccess", "UpdateProfile");
                return "cinema?command=INDEX_PAGE";
            }else {
                request.getSession().setAttribute("popUpsError", "UserAlreadyExist");
                return "cinema?command=PROFILE_PAGE";
            }
        }else {
            request.getSession().setAttribute("popUpsError", "InvalidEmail");
            return "cinema?command=PROFILE_PAGE";
        }
    }
}
