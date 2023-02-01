package controller.command.authCommand;

import controller.command.ICommand;
import controller.command.adminCommand.AdminSessionsPageCommand;
import controller.validator.Validator;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import model.enums.UserRole;
import org.apache.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

/**
 * Implementation of Command interface that perform to log in to account
 */
public class LogInCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(LogInCommand.class);
    private final UserService userService = ServiceFactory.getUserService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        logger.info("LogIn command");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if (Validator.isValidEmail(email) && Validator.isValidPassword(password)){
            if(userService.findByPasswordAndEmail(password,email)!=null){
                User user = userService.findByPasswordAndEmail(password,email);
                request.getSession().setAttribute("user", user);
                if (user.getRole()== UserRole.USER){
                    return "cinema?command=INDEX_PAGE";
                }else {
                    return "cinema?command=ADMIN_PAGE";
                }
            }else {
                request.getSession().setAttribute("popUpsError", "InvalidEmailOrPassword");
                return "cinema?command=LOG_IN_PAGE";
            }
        }else {
            request.getSession().setAttribute("popUpsError", "InvalidData");
            return "cinema?command=LOG_IN_PAGE";
        }
    }
}
