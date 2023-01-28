package controller.util;

import exception.DaoOperationException;
import jakarta.servlet.http.HttpServletRequest;
import service.LanguageService;
import service.ServiceFactory;

public class Localization {
    static LanguageService languageService = ServiceFactory.getLanguageService();
    public static void addLocalization(HttpServletRequest request) throws DaoOperationException {
        if(request.getSession().getAttribute("pageLanguage") == null){
            request.getSession().setAttribute("pageLanguage","eng");
            request.getSession().setAttribute("dbLanguage", languageService.getIdByName((String) request.getSession().getAttribute("pageLanguage")));
        }
        if (request.getParameter("changeLanguage")!=null){
            request.getSession().setAttribute("pageLanguage",request.getParameter("changeLanguage"));
            request.getSession().setAttribute("dbLanguage", languageService.getIdByName((String) request.getSession().getAttribute("pageLanguage")));
        }
    }
}
