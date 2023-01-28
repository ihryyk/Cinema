package controller.command.authCommand;

import controller.command.ICommand;
import controller.validator.Validator;
import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import org.apache.log4j.Logger;
import service.UserService;
import service.impl.UserServiceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Implementation of Command interface that perform registration
 */
public class RegisterCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(RegisterCommand.class);
    private final UserService userService = new UserServiceImpl();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException {
        logger.info("Register command");
        User user = getUserFromView(request);
        if (Validator.isValidEmail(user.getEmailAddress()) && Validator.isValidPhoneNumber(user.getPhoneNumber())
                && Validator.isValidPassword(user.getPassword()) && Validator.isValidName(user.getFirstName()) && Validator.isValidName(user.getLastName())){
                if (userService.findByEmail(user.getEmailAddress())!=null){
                    request.getSession().setAttribute("popUpsError", "A user with this email already exists");
                    return "cinema?command=REGISTER_PAGE";
                }else if (userService.findByPhoneNumber(user.getPhoneNumber())!=null){
                    request.getSession().setAttribute("popUpsError", "A user with this phone number already exists");
                    return "cinema?command=REGISTER_PAGE";
                }else {
                    userService.save(user);
                    request.getSession().setAttribute("user", userService.findByPasswordAndEmail(user.getPassword(),user.getEmailAddress()));
                    request.getSession().setAttribute("popUpsSuccess", "You are registered");
                    return "cinema?command=INDEX_PAGE";
                }

        }else {
            request.getSession().setAttribute("popUpsError", "You have entered invalid data");
            return "cinema?command=REGISTER_PAGE";
        }
    }


    /**
     * Get information about user from request.
     * @param request  {@link HttpServletRequest}.
     * @return user which contains the information of user for registration
     */
    private User getUserFromView(HttpServletRequest request){
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        User user = new User();
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setEmailAddress(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        return user;
    }
}
