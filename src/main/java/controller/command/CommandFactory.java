package controller.command;

import jakarta.servlet.http.HttpServletRequest;

/**
 * CommandFactory used for getting {@link ICommand}s to process requests.
 *
 */
public class CommandFactory {
    private CommandFactory() {}

    /**
     * Return {@link ICommand} by its corresponding command name.
     *
     * @param req  {@link HttpServletRequest}.
     * @return {@link ICommand}
     */
    public static ICommand getCommand(HttpServletRequest req) {
        String command = req.getParameter("command");
        ICommand iCommand = null;
        if (command != null) {
            try {
                iCommand = CommandEnum.valueOf(command).getCommand();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                iCommand = CommandEnum.ERROR_PAGE.getCommand();
            }
        } else {
            iCommand = CommandEnum.ERROR_PAGE.getCommand();
        }
        return iCommand;
    }
}
