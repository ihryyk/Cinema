package controller.command.authCommand;

import controller.command.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Implementation of Command interface that perform displaying register page.
 */
public class RegisterPageCommand implements ICommand {

    private final static Logger logger = Logger.getLogger(RegisterPageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Register page command");
        if(request.getSession().getAttribute("user")!=null){
            return "index";
        }
        return "registration";
    }
}
