package controller.util;

import jakarta.servlet.http.HttpServletRequest;

public class StartPosition {
    public static final int AMOUNT_MOVIE_ON_A_PAGE = 2;
    public final static String PAGE_NUMBER = "page";
    /**
     * @param req {@link HttpServletRequest}.
     * @return start position for pagination
     */
    public static int getStartPosition(HttpServletRequest req) {
        int page = 0;
        req.setAttribute("movieOnPage", AMOUNT_MOVIE_ON_A_PAGE);
        if ((req.getParameter(PAGE_NUMBER) != null)) {
            page = Integer.parseInt(req.getParameter(PAGE_NUMBER));
        }
        return page * AMOUNT_MOVIE_ON_A_PAGE;
    }
}
