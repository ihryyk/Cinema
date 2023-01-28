package controller.command.authCommand;

import controller.command.ICommand;
import controller.command.adminCommand.AdminSessionsPageCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Implementation of Command interface that perform displaying  log in page.
 */
public class LogInPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(LogInPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("LogIn page command");
        if(request.getSession().getAttribute("user")!=null){
           return "index";
        }
      return "login";
    }
}
