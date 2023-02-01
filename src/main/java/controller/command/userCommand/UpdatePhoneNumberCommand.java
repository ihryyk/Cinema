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
 * Implementation of a command line interface that performs phone number updates
 */
public class UpdatePhoneNumberCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(UpdatePhoneNumberCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("Update phone number command");
        String phoneNumber = request.getParameter("phoneNumber");
        long id = Long.parseLong(request.getParameter("id"));
        if (Validator.isValidPhoneNumber(phoneNumber)){
            if (userService.findByPhoneNumber(phoneNumber)==null){
                userService.updatePhoneNumber(phoneNumber,id);
                request.getSession().setAttribute("user", userService.findById(id));
                request.getSession().setAttribute("popUpsSuccess", "UpdateProfile");
                return "cinema?command=INDEX_PAGE";
            }else {
                request.getSession().setAttribute("popUpsError", "UserAlreadyExistWithTisNumber");
                return "cinema?command=PROFILE_PAGE";
            }
        }else {
            request.getSession().setAttribute("popUpsError", "InvalidPhoneNumber");
            return "cinema?command=PROFILE_PAGE";
        }
    }
}
