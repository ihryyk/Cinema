package util;

import model.entity.*;
import model.enums.MovieFormat;
import model.enums.UserRole;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetEntity {
    public static Movie getMovie() {
        Movie movie = new Movie();
        Language uaLanguage = new Language();
        uaLanguage.setId(1L);
        Language engLanguage = new Language();
        engLanguage.setId(2L);

        List<MovieDescription> movieDescriptionList = new ArrayList<>();

        MovieDescription engMovieDescription = new MovieDescription();
        engMovieDescription.setLanguage(engLanguage);
        engMovieDescription.setDirector("director");
        engMovieDescription.setTitle("title");
        movieDescriptionList.add(engMovieDescription);

        MovieDescription uaMovieDescription = new MovieDescription();
        uaMovieDescription.setLanguage(uaLanguage);
        uaMovieDescription.setDirector("режисер");
        uaMovieDescription.setTitle("назва");
        movieDescriptionList.add(uaMovieDescription);

        movie.setAvailableAge((short) 10);
        movie.setReleaseDate(Timestamp.valueOf(LocalDateTime.now()));
        movie.setOriginalName("movie");
        movie.setPoster(InputStream.nullInputStream());
        movie.setDeleted(false);
        movie.setMovieDescriptionList(movieDescriptionList);
        return movie;
    }

    public static Seat getSeat(){
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setNumber(1);
        seat.setNumber(1);
        return seat;
    }

    public static Session getSession() {
        Session session = new Session();
        Movie movie = new Movie();
        movie.setId(2L);

        session.setMovie(movie);
        session.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        session.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        session.setPrice(BigDecimal.valueOf(100));
        session.setAvailableSeats(2);
        session.setFormat(MovieFormat.D2);
        return session;
    }

    public static User getUser (){
        User user = new User();
        user.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setRole(UserRole.USER);
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmailAddress("email2");
        user.setPhoneNumber("phone2");
        user.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setPassword("password2");
        return user;
    }

    public static Ticket getTicket(){
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now()));
        ticket.setUser(getUser());
        return ticket;
    }
}
