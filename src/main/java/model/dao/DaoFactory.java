package model.dao;

import model.dao.impl.*;

public class DaoFactory {

    private static DaoFactory instance = new DaoFactory();

    private final UserDao userDao = new UserDaoImpl();
    private final MovieDao movieDao = new MovieDaoImpl();
    private final PurchasedSeatDao purchasedSeatDao = new PurchasedSeatDaoImpl();
    private final SeatDao seatDao = new SeatDaoImpl();
    private final SessionDao sessionDao = new SessionDaoImpl();
    private final TicketDao ticketDao = new TicketDaoImpl();
    private final LanguageDao languageDao = new LanguageDaoImpl();

    private DaoFactory(){};

    public synchronized static DaoFactory getInstance() {
        if (instance == null)
            instance = new DaoFactory();
        return instance;
    }

    public static UserDao getUserDao() {
        return getInstance().userDao;
    }

    public static MovieDao getMovieDao(){
        return getInstance().movieDao;
    }

    public static PurchasedSeatDao getPurchasedSeatDao(){
        return getInstance().purchasedSeatDao;
    }

    public static SeatDao getSeatDao(){
        return getInstance().seatDao;
    }

    public static SessionDao getSessionDao(){
        return getInstance().sessionDao;
    }

    public static TicketDao getTicketDao(){
        return getInstance().ticketDao;
    }

    public static LanguageDao getLanguageDao() {return getInstance().languageDao;}
}
