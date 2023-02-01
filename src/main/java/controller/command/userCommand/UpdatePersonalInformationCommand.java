package controller.command.userCommand;

import controller.command.ICommand;
import controller.validator.Validator;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

import java.io.IOException;

/**
 * Implementation of a command line interface that performs personal information updates
 */
public class UpdatePersonalInformationCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(UpdatePersonalInformationCommand.class);
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException, TransactionException {
        logger.info("Update personal information command");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        long id = Long.parseLong(request.getParameter("id"));
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        if (Validator.isValidPassword(user.getPassword()) && Validator.isValidName(user.getFirstName()) && Validator.isValidName(user.getLastName())){
            userService.updateContactInformation(user);
            request.getSession().setAttribute("user", userService.findById(id));
            request.getSession().setAttribute("popUpsSuccess", "UpdateProfile");
            return "cinema?command=INDEX_PAGE";
        }else {
            request.getSession().setAttribute("popUpsError", "InvalidPersonalInformation");
            return "cinema?command=PROFILE_PAGE";
        }
    }
}
