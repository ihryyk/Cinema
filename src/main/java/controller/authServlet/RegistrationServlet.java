package controller.authServlet;

import controller.validator.Validator;
import exception.ServiceException;
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

@WebServlet("/cinema/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = getUserFromView(request);
        if (Validator.isValidEmail(user.getEmailAddress()) && Validator.isValidPhoneNumber(user.getPhoneNumber())
                && Validator.isValidPassword(user.getPassword()) && Validator.isValidName(user.getFirstName()) && Validator.isValidName(user.getLastName())){
            try {
                if (userService.findByEmail(user.getEmailAddress())!=null){
                    request.getSession().setAttribute("popUps", "A user with this email already exists");
                    response.sendRedirect("/cinema/registration");
                }else if (userService.findByPhoneNumber(user.getPhoneNumber())!=null){
                    request.getSession().setAttribute("popUps", "A user with this phone number already exists");
                    response.sendRedirect("/cinema/registration");
                }else {
                    userService.save(user);
                    request.getSession().setAttribute("user", userService.findByPasswordAndEmail(user.getPassword(),user.getEmailAddress()));
                    response.sendRedirect("/cinema");
                }
            } catch (ServiceException e) {
                response.sendRedirect("/cinema/error");
            }
        }else {
            request.getSession().setAttribute("popUps", "You have entered invalid data");
            response.sendRedirect("/cinema/registration");
        }
    }

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