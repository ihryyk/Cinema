package controller.authServlet;

import controller.validator.Validator;
import exception.DaoOperationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Servlet for user registration in the system.
 */
@WebServlet("/cinema/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = getUserFromView(request);
        if (Validator.isValidEmail(user.getEmailAddress()) && Validator.isValidPhoneNumber(user.getPhoneNumber())
                && Validator.isValidPassword(user.getPassword()) && Validator.isValidName(user.getFirstName()) && Validator.isValidName(user.getLastName())){
            try {
                if (userService.findByEmail(user.getEmailAddress())!=null){
                    request.getSession().setAttribute("popUpsError", "A user with this email already exists");
                    response.sendRedirect("/cinema/registration");
                }else if (userService.findByPhoneNumber(user.getPhoneNumber())!=null){
                    request.getSession().setAttribute("popUpsError", "A user with this phone number already exists");
                    response.sendRedirect("/cinema/registration");
                }else {
                    userService.save(user);
                    request.getSession().setAttribute("user", userService.findByPasswordAndEmail(user.getPassword(),user.getEmailAddress()));
                    request.getSession().setAttribute("popUpsSuccess", "You are registered");
                    response.sendRedirect("/cinema");
                }
            } catch (DaoOperationException e) {
                response.sendRedirect("/cinema/error");
            }
        }else {
            request.getSession().setAttribute("popUpsError", "You have entered invalid data");
            response.sendRedirect("/cinema/registration");
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