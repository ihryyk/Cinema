package controller;

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
            }
        }catch (ServiceException | ServletException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            switch (request.getPathInfo()) {
                case ("/ticket"):
                    acceptTicket(request);
                    response.sendRedirect("/cinema");
                    break;
            }
        }catch (ServiceException e){
            throw new RuntimeException(e);
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
