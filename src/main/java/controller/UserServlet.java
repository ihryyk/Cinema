package controller;

import controller.validator.Validator;
import exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.entity.Seat;
import model.entity.User;
import service.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/cinema/user/*")
public class UserServlet extends HttpServlet {
    private final SeatService seatService = ServiceFactory.getSeatService();
    private final PurchasedSeatService purchasedSeatService = ServiceFactory.getPurchasedSeatService();
    private final SessionService sessionService = ServiceFactory.getSessionService();
    private final UserService userService = ServiceFactory.getUserService();
    private final TicketService ticketService = ServiceFactory.getTicketService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/seat"):
                    getSeats(request);
                    request.getRequestDispatcher("/views/seat.jsp").forward(request, response);
                    break;
                case ("/ticket"):
                    ticketFormation(request);
                    request.getRequestDispatcher("/views/ticket.jsp").forward(request, response);
                    break;
                case ("/tickets"):
                    showAllUserTickets(request);
                    request.getRequestDispatcher("/views/userTickets.jsp").forward(request, response);
                    break;
                case ("/profile"):
                    request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
            }
        }catch (ServiceException | ServletException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = null;
        try{
            switch (request.getPathInfo()) {
                case ("/ticket"):
                    acceptTicket(request);
                    response.sendRedirect("/cinema");
                    break;
                case ("/profile/email"):
                    updateEmail(request,response);
                    break;
                case ("/profile/phoneNumber"):
                    updatePhoneNumber(request,response);
                    break;
                case ("/profile/personalInformation"):
                    updatePersonalInformation(request,response);
                    break;
            }
        }catch (ServiceException e){
            throw new RuntimeException(e);
        }
    }
    private void updateEmail(HttpServletRequest request, HttpServletResponse response ) throws IOException, ServiceException {
        String email = request.getParameter("email");
        Long id = Long.valueOf(request.getParameter("id"));
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
    private void updatePhoneNumber(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        String phoneNumber = request.getParameter("phoneNumber");
        Long id = Long.valueOf(request.getParameter("id"));
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

    private void updatePersonalInformation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Long id = Long.valueOf(request.getParameter("id"));
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

    private void showAllUserTickets(HttpServletRequest request) throws ServiceException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("tickets", ticketService.findByUserId(user.getId(),1l) );
    }

    private void acceptTicket(HttpServletRequest request) throws ServiceException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        Long seatId = Long.valueOf(request.getParameter("seatId"));
        User user = (User) request.getSession().getAttribute("user");
        purchasedSeatService.save(seatId,sessionId,user.getId());
        request.getSession().setAttribute("popUps","The ticket is accepted");
    }

    private void ticketFormation(HttpServletRequest request) throws ServiceException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        Long seatId = Long.valueOf(request.getParameter("seatId"));
        request.setAttribute("session",sessionService.findById(sessionId,1L));
        request.setAttribute("seat", seatService.findById(seatId));
    }

    private void getSeats(HttpServletRequest request) throws ServiceException {
        Long sessionId = Long.valueOf(request.getParameter("sessionId"));
        List<Seat> seats = seatService.findAllFreeSeatForSession(sessionId);
        request.setAttribute("seats", seats);
        request.setAttribute("session",sessionService.findById(sessionId,1L));
    }
}
