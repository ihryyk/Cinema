package controller;

import controller.validator.Validator;
import exception.DaoOperationException;
import exception.TransactionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.User;
import service.*;

import java.io.IOException;

/**
 * Functions which are used by user.
 */
@WebServlet("/cinema/user/*")
public class UserServlet extends HttpServlet {
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final PurchasedSeatService purchasedSeatService = ServiceFactory.getPurchasedSeatService();
    private final SessionService sessionService = ServiceFactory.getSessionService();
    private final UserService userService = ServiceFactory.getUserService();
    private final TicketService ticketService = ServiceFactory.getTicketService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/ticket"):
                    ticketFormation(request, response);
                    break;
                case ("/tickets"):
                    showAllUserTickets(request, response);
                    break;
                case ("/profile"):
                    request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
                    break;
            }
        }catch (ServletException | DaoOperationException e){
            response.sendRedirect("/cinema/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/ticket"):
                    acceptTicket(request, response);
                    break;
                case ("/profile/email"):
                    updateEmail(request, response);
                    break;
                case ("/profile/phoneNumber"):
                    updatePhoneNumber(request,response);
                    break;
                case ("/profile/personalInformation"):
                    updatePersonalInformation(request,response);
                    break;
            }
        }catch (DaoOperationException | TransactionException e){
            response.sendRedirect("/cinema/error");
        }
    }

    /**
     * Updates user's email information.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void updateEmail(HttpServletRequest request, HttpServletResponse response) throws IOException, DaoOperationException {
        String email = request.getParameter("email");
        long id = Long.parseLong(request.getParameter("id"));
        if (Validator.isValidEmail(email)){
            if (userService.findByEmail(email)==null){
                userService.updateEmail(email,id);
                request.getSession().setAttribute("user", userService.findById(id));
                request.getSession().setAttribute("popUpsSuccess", "Your profile has been updated");
                response.sendRedirect("/cinema");
            }else {
                request.getSession().setAttribute("popUpsError", "A user with this email address already exists");
                response.sendRedirect("/cinema/user/profile");
            }
        }else {
            request.getSession().setAttribute("popUpsError", "You have entered invalid email");
            response.sendRedirect("/cinema/user/profile");
        }
    }

    /**
     * Updates user's phone number information.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void updatePhoneNumber(HttpServletRequest request, HttpServletResponse response) throws IOException, DaoOperationException {
        String phoneNumber = request.getParameter("phoneNumber");
        long id = Long.parseLong(request.getParameter("id"));
        if (Validator.isValidPhoneNumber(phoneNumber)){
            if (userService.findByPhoneNumber(phoneNumber)==null){
                userService.updatePhoneNumber(phoneNumber,id);
                request.getSession().setAttribute("user", userService.findById(id));
                request.getSession().setAttribute("popUpsSuccess", "Your profile has been updated");
                response.sendRedirect("/cinema");
            }else {
                request.getSession().setAttribute("popUpsError", "A user with this phone number already exists");
                response.sendRedirect("/cinema/user/profile");
            }
        }else {
            request.getSession().setAttribute("popUpsError", "You have entered invalid phone number");
            response.sendRedirect("/cinema/user/profile");
        }
    }

    /**
     * Updates user's password, first name, last name .
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void updatePersonalInformation(HttpServletRequest request, HttpServletResponse response) throws IOException, DaoOperationException {
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        long id = Long.parseLong(request.getParameter("id"));
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        if (Validator.isValidPassword(user.getPassword()) && Validator.isValidName(user.getFirstName()) && Validator.isValidName(user.getLastName())){
            userService.updateContactInformation(user);
            request.getSession().setAttribute("user", userService.findById(id));
            request.getSession().setAttribute("popUpsSuccess", "Your profile has been updated");
            response.sendRedirect("/cinema");
        }else {
            request.getSession().setAttribute("popUpsError", "You have entered personal information phone number");
            response.sendRedirect("/cinema/user/profile");
        }
    }

    /**
     * Adds all information abut user's tickets to request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs
     */
    private void showAllUserTickets(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("tickets", ticketService.findByUserId(user.getId(),1l) );
        request.getRequestDispatcher("/views/userTickets.jsp").forward(request, response);
    }

    /**
     * Save new ticket.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     */
    private void acceptTicket(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, TransactionException, IOException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        Long seatId = Long.valueOf(request.getParameter("seatId"));
        User user = (User) request.getSession().getAttribute("user");
        purchasedSeatService.save(seatId,sessionId,user.getId());
        request.getSession().setAttribute("popUps","The ticket is accepted");
        response.sendRedirect("/cinema");
    }

    /**
     * Adds all information abut new ticket to request.
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @throws DaoOperationException if there was an error executing the query
     *                               in the DAO
     * @throws IOException           if I/O error occurs.
     * @throws ServletException      if Servlet error occurs
     */
    private void ticketFormation(HttpServletRequest request, HttpServletResponse response) throws DaoOperationException, ServletException, IOException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        Long seatId = Long.valueOf(request.getParameter("seatId"));
        request.setAttribute("session",sessionService.findByIdAndLanguageId(sessionId,1L));
        request.setAttribute("seat", seatService.findById(seatId));
        request.getRequestDispatcher("/views/ticket.jsp").forward(request, response);
    }


}
