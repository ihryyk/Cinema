package controller.command;

import controller.command.adminCommand.*;
import controller.command.authCommand.*;
import controller.command.unregisnterUserCommand.ErrorPageCommand;
import controller.command.unregisnterUserCommand.IndexPageCommand;
import controller.command.unregisnterUserCommand.SeatsPageCommand;
import controller.command.unregisnterUserCommand.SessionsPageCommand;
import controller.command.userCommand.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains the names of the commands.
 *
 */
public enum CommandEnum {
    LOG_IN_PAGE(new LogInPageCommand()),
    LOG_OUT(new LogOutCommand()),
    LOG_IN(new LogInCommand()),
    ERROR_PAGE(new ErrorPageCommand()),
    REGISTER(new RegisterCommand()),
    REGISTER_PAGE(new RegisterPageCommand()),

    //admin
    ADMIN_PAGE(new AdminPageCommand()),
    ADD_MOVIE_PAGE(new AddMoviePageCommand()),
    ADD_SESSION_PAGE(new AddSessionPageCommand()),
    ADMIN_SEATS_PAGE(new AdminSeatsPageCommand()),
    ADMIN_SESSIONS_PAGE(new AdminSessionsPageCommand()),
    UPDATE_MOVIE_PAGE(new UpdateMoviePageCommand()),
    UPDATE_SESSION_PAGE(new UpdateSessionPageCommand()),
    ADD_MOVIE(new AddMovieCommand()),
    ADD_SESSION(new AddSessionCommand()),
    CANCEL_MOVIE(new CancelMovieCommand()),
    UPDATE_MOVIE(new UpdateMovieCommand()),
    UPDATE_SESSION(new UpdateSessionCommand()),

    //user
    ACCEPT_TICKET(new AcceptTicketCommand()),
    PROFILE_PAGE(new ProfilePageCommand()),
    TICKETS_PAGE(new TicketsPageCommand()),
    UPDATE_EMAIL(new UpdateEmailCommand()),
    UPDATE_PERSONAL_INFORMATION(new UpdatePersonalInformationCommand()),
    UPDATE_PHONE_NUMBER(new UpdatePhoneNumberCommand()),
    TICKET_FORMATION_PAGE(new TicketFormationCommand()),

    //unregisterUser
    INDEX_PAGE(new IndexPageCommand()),
    SEATS_PAGE(new SeatsPageCommand()),
    SESSIONS_PAGE(new SessionsPageCommand());


    private final ICommand command;

    CommandEnum(ICommand command) {
        this.command = command;
    }

    /**
     * Returns list of command which can do admin.
     *
     * @return  list of command which can do admin.
     */
    public static List<String> getAdminCommand() {
        List<String> adminCommand = new ArrayList<>();
        adminCommand.add(ADMIN_PAGE.name());
        adminCommand.add(ADD_MOVIE_PAGE.name());
        adminCommand.add(ADD_SESSION_PAGE.name());
        adminCommand.add(ADMIN_SEATS_PAGE.name());
        adminCommand.add(ADMIN_SESSIONS_PAGE.name());
        adminCommand.add(UPDATE_MOVIE_PAGE.name());
        adminCommand.add(UPDATE_SESSION_PAGE.name());
        adminCommand.add(ADD_MOVIE.name());
        adminCommand.add(ADD_SESSION.name());
        adminCommand.add(CANCEL_MOVIE.name());
        adminCommand.add(UPDATE_MOVIE.name());
        adminCommand.add(UPDATE_SESSION.name());
        return adminCommand;
    }

    /**
     * Returns list of command which can do user.
     *
     * @return  list of command which can do user.
     */
    public static List<String> getUserCommand() {
        List<String> userCommand = new ArrayList<>();
        userCommand.add(ACCEPT_TICKET.name());
        userCommand.add(PROFILE_PAGE.name());
        userCommand.add(TICKETS_PAGE.name());
        userCommand.add(UPDATE_EMAIL.name());
        userCommand.add(UPDATE_PERSONAL_INFORMATION.name());
        userCommand.add(UPDATE_PHONE_NUMBER.name());
        userCommand.add(TICKET_FORMATION_PAGE.name());
        return userCommand;
    }

    public ICommand getCommand() {
        return command;
    }
}
