package controller.authServlet;

import controller.validator.Validator;
import exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import model.enums.UserRole;
import service.ServiceFactory;
import service.UserService;

import java.io.IOException;
@WebServlet("/cinema/login")
public class LogInServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user")!=null){
            req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if (Validator.isValidEmail(email) && Validator.isValidPassword(password)){
            try {
                if(userService.findByPasswordAndEmail(password,email)!=null){
                    User user = userService.findByPasswordAndEmail(password,email);
                    request.getSession().setAttribute("user", user);
                    if (user.getRole()==UserRole.USER){
                    response.sendRedirect("/cinema");
                    }else {
                        response.sendRedirect("/cinema/admin");
                    }
                }else {
                    request.getSession().setAttribute("popUpsError", "User with this email and password does not exist");
                    response.sendRedirect("/cinema/login");
                }
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        }else {
            request.getSession().setAttribute("popUpsError", "You have entered invalid data");
            response.sendRedirect("/cinema/login");
        }
    }
}
