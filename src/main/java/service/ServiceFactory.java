package service;

import model.dao.UserDao;
import service.impl.*;

public class ServiceFactory {

    /**
     * ServiceFactory instance.
     */
    private static ServiceFactory instance = new ServiceFactory();

    /**
     * {@link UserService} field.
     */
    private final UserService userService = new UserServiceImpl();

    /**
     * {@link MovieService} field.
     */
    private final MovieService movieService = new MovieServiceImpl();

    /**
     * {@link LanguageService} field.
     */
    private final LanguageService languageService = new LanguageServiceImpl();

    /**
     * {@link SessionService} field.
     */
    private final SessionService sessionService = new SessionServiceImpl();

    /**
     * {@link SeatService} field.
     */
    private final SeatService seatService = new SeatServiceImpl();

    /**
     * {@link PurchasedSeatService} field.
     */
    private final PurchasedSeatService purchasedSeatService = new PurchasedSeatServiceImpl();

    /**
     * {@link TicketService} field.
     */
    private final TicketService ticketService = new TicketServiceImpl();
    private ServiceFactory(){};

    /**
     * Return ServiceFactory instance.
     *
     * @return ServiceFactory instance.
     */
    public synchronized static ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();
        return instance;
    }

    /**
     * Return {@link  UserService} implementation.
     *
     * @return {@link  UserService} implementation.
     */
    public static UserService getUserService () {
        return getInstance().userService;
    }

    /**
     * Return {@link  MovieService} implementation.
     *
     * @return {@link  MovieService} implementation.
     */
    public static MovieService getMovieService() {return getInstance().movieService;}

    /**
     * Return {@link  LanguageService} implementation.
     *
     * @return {@link  LanguageService} implementation.
     */
    public static LanguageService getLanguageService() {return getInstance().languageService;}

    /**
     * Return {@link  SessionService} implementation.
     *
     * @return {@link  SessionService} implementation.
     */
    public static SessionService getSessionService() {return getInstance().sessionService;}

    /**
     * Return {@link  SeatService} implementation.
     *
     * @return {@link  SeatService} implementation.
     */
    public static SeatService getSeatService() {return getInstance().seatService;}

    /**
     * Return {@link  PurchasedSeatService} implementation.
     *
     * @return {@link PurchasedSeatService} implementation.
     */
    public static PurchasedSeatService getPurchasedSeatService() {return getInstance().purchasedSeatService;}

    /**
     * Return {@link  TicketService} implementation.
     *
     * @return {@link  TicketService} implementation.
     */
    public static TicketService getTicketService() {return getInstance().ticketService;}
}
