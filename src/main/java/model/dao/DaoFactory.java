package model.dao;

import model.dao.impl.*;

public class DaoFactory {

    /**
     * DaoFactory instance.
     */
    private static DaoFactory instance = new DaoFactory();

    /**
     * {@link UserDao} field.
     */
    private final UserDao userDao = new UserDaoImpl();

    /**
     * {@link MovieDao} field.
     */
    private final MovieDao movieDao = new MovieDaoImpl();

    /**
     * {@link PurchasedSeatDao} field.
     */
    private final PurchasedSeatDao purchasedSeatDao = new PurchasedSeatDaoImpl();

    /**
     * {@link SeatDao} field.
     */
    private final SeatDao seatDao = new SeatDaoImpl();

    /**
     * {@link SessionDao} field.
     */
    private final SessionDao sessionDao = new SessionDaoImpl();

    /**
     * {@link TicketDao} field.
     */
    private final TicketDao ticketDao = new TicketDaoImpl();

    /**
     * {@link LanguageDao} field.
     */
    private final LanguageDao languageDao = new LanguageDaoImpl();

    /**
     * {@link MovieDescriptionDao} field.
     */
    private final MovieDescriptionDao movieDescriptionDao = new MovieDescriptionDaoImpl();

    private DaoFactory(){};

    /**
     * Return DaoFactory instance.
     *
     * @return DaoFactory instance.
     */
    public synchronized static DaoFactory getInstance() {
        if (instance == null)
            instance = new DaoFactory();
        return instance;
    }

    /**
     * Return {@link  UserDao} implementation.
     *
     * @return {@link  UserDao} implementation.
     */
    public static UserDao getUserDao() {
        return getInstance().userDao;
    }

    /**
     * Return {@link  MovieDao} implementation.
     *
     * @return {@link  MovieDao} implementation.
     */
    public static MovieDao getMovieDao(){
        return getInstance().movieDao;
    }

    /**
     * Return {@link  PurchasedSeatDao} implementation.
     *
     * @return {@link  PurchasedSeatDao} implementation.
     */
    public static PurchasedSeatDao getPurchasedSeatDao(){
        return getInstance().purchasedSeatDao;
    }

    /**
     * Return {@link  SeatDao} implementation.
     *
     * @return {@link  SeatDao} implementation.
     */
    public static SeatDao getSeatDao(){
        return getInstance().seatDao;
    }

    /**
     * Return {@link SessionDao} implementation.
     *
     * @return {@link  SessionDao} implementation.
     */
    public static SessionDao getSessionDao(){
        return getInstance().sessionDao;
    }

    /**
     * Return {@link  TicketDao} implementation.
     *
     * @return {@link  TicketDao} implementation.
     */
    public static TicketDao getTicketDao(){
        return getInstance().ticketDao;
    }

    /**
     * Return {@link  LanguageDao} implementation.
     *
     * @return {@link  LanguageDao} implementation.
     */
    public static LanguageDao getLanguageDao() {return getInstance().languageDao;}

    /**
     * Return {@link  MovieDescriptionDao} implementation.
     *
     * @return {@link  MovieDescriptionDao} implementation.
     */
    public static MovieDescriptionDao getMovieDescriptionDao(){return getInstance().movieDescriptionDao;}
}
