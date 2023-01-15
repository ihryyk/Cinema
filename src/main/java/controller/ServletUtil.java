package controller;

import exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import service.MovieService;
import service.ServiceFactory;

public class ServletUtil {
    private static final MovieService movieService = ServiceFactory.getMovieService();
    public static void setMovieInRequest(HttpServletRequest request) throws ServiceException {

    }
}
