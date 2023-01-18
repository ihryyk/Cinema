package controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.log4j.Logger;

/**
 * Session attribute listener. Implementation of {@link HttpSessionAttributeListener}.
 */
@WebListener
public class NotificationListener implements HttpSessionAttributeListener {
    private static final Logger logger = Logger.getLogger(NotificationListener.class);

    /**
     * Receives notification that an attribute has been added to a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     *              and the name and value of the attribute that was added
     */
    @Override
    public void attributeAdded(final HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Session attribute added: ").append(event.getClass().getSimpleName()).append(" : ").append(event.getName())
                .append(" : ").append(event.getValue());
        logger.info(sb);
    }

    /**
     * Receives notification that an attribute has been changed from a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     * and the name and value of the attribute that was removed
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(":").append(event.getClass().getSimpleName()).append(":").append(event.getName()).
                append(":").append(event.getValue());
        logger.info(sb);
    }


    /**
     * Receives notification that an attribute has been removed from a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     * and the name and value of the attribute that was removed
     */
    @Override
    public void attributeRemoved(final HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Session attribute removed: ").append(event.getClass().getSimpleName()).append(" : ").append(event.getName()).
                append(" : ").append(event.getValue());
        logger.info(sb);
    }
}
