package service;

import model.dao.*;
import model.dao.impl.*;
import service.impl.*;

public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final MovieService movieService = new MovieServiceImpl();
    private final LanguageService languageService = new LanguageServiceImpl();
    private final SessionService sessionService = new SessionServiceImpl();
    private final SeatService seatService = new SeatServiceImpl();
    private final PurchasedSeatService purchasedSeatService = new PurchasedSeatImpl();
    private final TicketService ticketService = new TicketServiceImpl();
    private ServiceFactory(){};

    public synchronized static ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();
        return instance;
    }

    public static UserService getUserService () {
        return getInstance().userService;
    }
    public static MovieService getMovieService() {return getInstance().movieService;}
    public static LanguageService getLanguageService() {return getInstance().languageService;}
    public static SessionService getSessionService() {return getInstance().sessionService;}
    public static SeatService getSeatService() {return getInstance().seatService;}
    public static PurchasedSeatService getPurchasedSeatService() {return getInstance().purchasedSeatService;}
    public static TicketService getTicketService() {return getInstance().ticketService;}
}
